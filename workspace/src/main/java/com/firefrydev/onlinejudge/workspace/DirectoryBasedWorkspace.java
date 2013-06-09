package com.firefrydev.onlinejudge.workspace;

import com.firefrydev.onlinejudge.helper.core.*;
import com.firefrydev.onlinejudge.workspace.utils.ClassLoadUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DirectoryBasedWorkspace implements Workspace {
    private final OnlineJudgeSystem onlineJudgeSystem;
    private final File workspaceDir;
    private final Author author;
    private String problemId;

    public DirectoryBasedWorkspace(OnlineJudgeSystem onlineJudgeSystem, File workspaceDir, Author author) {
        this.onlineJudgeSystem = onlineJudgeSystem;
        this.workspaceDir = workspaceDir;
        this.author = author;
    }

    @Override
    public void switchTo(String problemId) {
        this.problemId = problemId;
    }

    @Override
    public void init(Language language) throws IOException {
        if (problemId == null) {
            throw new NullPointerException("Current problem is undefined");
        }
        File dir = new File(workspaceDir, problemId);
        if (dir.exists()) {
            throw new IllegalStateException("Directory is already exists!");
        }
        addSetting("language", language.toString());
        initProblem(dir);
        createLanguageTemplate(dir, language);
    }

    private void addSetting(String key, String value) throws IOException {
        Map<String, String> map = readSettings();
        map.put(key, value);
        writeSettings(map);
    }

    private Map<String, String> readSettings() throws IOException {
        File setting = new File(new File(workspaceDir, problemId), "settings.txt");
        if (!setting.exists()) {
            return new HashMap<String, String>();
        }
        List<String> lines = FileUtils.readLines(setting);
        Map<String, String> map = new HashMap<String, String>(lines.size());
        for (String line : lines) {
            String[] values = line.split("=");
            if (values.length == 2) {
                map.put(values[0].trim(), values[1].trim());
            }
        }
        return map;
    }

    private void writeSettings(Map<String, String> map) throws IOException {
        File setting = new File(new File(workspaceDir, problemId), "settings.txt");
        StringBuilder stringBuilder = new StringBuilder();
        for (String eachKey : map.keySet()) {
            stringBuilder
                    .append(eachKey)
                    .append(" = ")
                    .append(map.get(eachKey))
                    .append("\n");
        }
        if (setting.exists()) {
            FileUtils.forceDelete(setting);
        }
        FileUtils.writeStringToFile(setting, stringBuilder.toString());
    }

    private void initProblem(File dir) throws IOException {
        Problem problem = onlineJudgeSystem.getProblem(problemId);
        FileUtils.writeStringToFile(new File(dir, "description.txt"), problem.toString());
        createTestFiles(dir, problem);
    }

    private void createTestFiles(File dir, Problem problem) throws IOException {
        File tests = new File(dir, "tests");
        int index = 0;
        for (SampleTest sampleTest : problem.getSampleTests()) {
            File testDir = new File(tests, String.valueOf(index));
            if (!testDir.exists()) {
                FileUtils.writeStringToFile(new File(testDir, "input.txt"), sampleTest.getInput() + "\r\n");
                FileUtils.writeStringToFile(new File(testDir, "output.txt"), sampleTest.getOutput() + "\r\n");
            }
            index++;
        }
    }

    private void createLanguageTemplate(File dir, Language language) throws IOException {
        LanguageSettings languageSettings = onlineJudgeSystem.getLanguageSettings(language);
        String resourceName =
                new StringBuilder(File.separator)
                        .append("templates")
                        .append(File.separator)
                        .append(languageSettings.toString())
                        .append(File.separator)
                        .append("template.txt")
                        .toString();
        InputStream templateStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (templateStream == null) {
            throw new IllegalStateException("Cannot find a template: " + resourceName);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream));
        File problemSource = new File(dir, getProblemFileName());
        problemSource.createNewFile();
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(problemSource)));
        String line;
        while ((line = reader.readLine()) != null) {
            printWriter.append(line.replace("%id%", String.valueOf(problemId)) + "\n");
        }
        printWriter.close();
        reader.close();
    }

    @Override
    public String getCurrentProblem() {
        return problemId;
    }

    @Override
    public TestResult[] test() {
        Class solutionClass;
        try {
            solutionClass = ClassLoadUtils.load(new File(workspaceDir, problemId).getPath(), getProblemClassName());
        } catch (Exception e) {
            return new TestResult[] {new TestResult("unknown", false, "Failed to load solution class: " + e)};
        }
        return test(solutionClass);
    }

    @Override
    public TestResult[] test(Class solution) {
        try {
            File[] tests = new File(new File(workspaceDir, problemId), "tests").listFiles();
            List<TestResult> results = new LinkedList<TestResult>();
            for (File test : tests) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                FileInputStream fileInputStream = new FileInputStream(new File(test, "input.txt"));
                Object solver = solution.getConstructor(InputStream.class, OutputStream.class).newInstance(fileInputStream, byteArrayOutputStream);
                solution.getMethod("run").invoke(solver);
                fileInputStream.close();
                byteArrayOutputStream.close();
                String output = byteArrayOutputStream.toString();
                String expectedOutput = FileUtils.readFileToString(new File(test, "output.txt"));
                if (expectedOutput.equals(output)) {
                    results.add(new TestResult(test.getName(), true, "Accepted"));
                } else {
                    TestResult result = getTestResult(test, output, expectedOutput);
                    results.add(result);
                }
            }
            return results.toArray(new TestResult[results.size()]);
        } catch (Exception e) {
            return new TestResult[] {new TestResult("unknown", false, "Exception while testing the solution class: " + e)};
        }
    }

    private TestResult getTestResult(File test, String output, String expectedOutput) {
        String[] lines1 = output.split("\n");
        String[] lines2 = expectedOutput.split("\n");
        int i;
        for (i = 0; i < Math.min(lines1.length, lines2.length); i++) {
            if (!lines1[i].equals(lines2[i])) {
                return new TestResult(test.getName(), false, "Outputs are not equals" +
                        "\nExpected: " + lines2[i] +
                        "\nFound:    " + lines1[i]);
            }
        }
        return new TestResult(test.getName(), false, "Outputs are not equals" +
                "\nExpected: " + (i < lines2.length ? lines2[i] : null) +
                "\nFound:    " + (i < lines1.length ? lines1[i] : null));
    }

    @Override
    public CommitResult commit() throws IOException, InterruptedException {
        String lang = readSettings().get("language");
        if (lang == null) {
            throw new IllegalStateException("Settings file is broken");
        }
        Language language = Language.valueOf(lang);
        String source = FileUtils.readFileToString(new File(new File(workspaceDir, problemId), getProblemFileName()));
        return onlineJudgeSystem.perform(new Commit(author, problemId, language, source));
    }

    private String getProblemFileName() throws IOException {
        return getProblemClassName() + "." + onlineJudgeSystem.getLanguageSettings(Language.valueOf(readSettings().get("language"))).getExtension();
    }

    private String getProblemClassName() {
        return "Problem" + problemId;
    }
}

