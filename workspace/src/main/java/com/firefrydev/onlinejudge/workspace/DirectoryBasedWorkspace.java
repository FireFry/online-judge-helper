package com.firefrydev.onlinejudge.workspace;

import com.firefrydev.onlinejudge.helper.core.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
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
        initProblem(dir);
        createLanguageTemplate(dir, language);
        addSetting("language", language.toString());
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
                FileUtils.writeStringToFile(new File(testDir, "input.txt"), sampleTest.getInput() + "\n");
                FileUtils.writeStringToFile(new File(testDir, "output.txt"), sampleTest.getOutput() + "\n");
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
        File problemSource = new File(dir, getProblemSourceName(languageSettings));
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
        return new TestResult[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TestResult[] test(Class solution) {
        return new TestResult[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CommitResult commit() throws IOException, InterruptedException {
        String lang = readSettings().get("language");
        if (lang == null) {
            throw new IllegalStateException("Settings file is broken");
        }
        Language language = Language.valueOf(lang);
        LanguageSettings languageSettings = onlineJudgeSystem.getLanguageSettings(language);
        String source = FileUtils.readFileToString(new File(new File(workspaceDir, problemId), getProblemSourceName(languageSettings)));
        return onlineJudgeSystem.perform(new Commit(author, problemId, language, source));
    }

    private String getProblemSourceName(LanguageSettings languageSettings) {
        return "Problem" + problemId + "." + languageSettings.getExtension();
    }
}

