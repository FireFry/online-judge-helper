package com.firefrydev.onlinejudge.helper.workspace;

import com.firefrydev.onlinejudge.helper.core.*;
import com.firefrydev.onlinejudge.helper.workspace.utils.ClassLoadUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class DirectoryBasedWorkspace implements Workspace {
    public static final String CURRENT_PROBLEM_ID_KEY = "currentProblemId";
    public static final String PROBLEM_CLASS_NAME_PREFIX = "Problem";
    public static final String LANGUAGE_KEY = "language";
    public static final String SETTINGS_FILE_NAME = "settings.txt";
    public static final String PRIVATE_DIR_NAME = ".ojh";
    public static final String DESCRIPTION_FILE_NAME = "Description.txt";
    public static final String TESTS_DIR_NAME = "tests";
    public static final String INPUT_FILE_NAME = "input.txt";
    public static final String OUTPUT_FILE_NAME = "output.txt";
    public static final String TEMPLATES_DIR_NAME = "templates";
    public static final String TEMPLATE_FILE_NAME = "template.txt";
    public static final String ID_KEY = "%id%";

    private final OnlineJudgeSystem onlineJudgeSystem;
    private final File workspaceDir;
    private final Author author;
    private final SettingsFile settings;

    public DirectoryBasedWorkspace(OnlineJudgeSystem onlineJudgeSystem, File workspaceDir, Author author) {
        this.onlineJudgeSystem = onlineJudgeSystem;
        this.workspaceDir = workspaceDir;
        this.author = author;
        this.settings = new SettingsFile(new File(new File(workspaceDir, PRIVATE_DIR_NAME), SETTINGS_FILE_NAME));
    }

    @Override
    public void switchTo(String problemId) throws IOException {
        setProblemId(problemId);
    }

    @Override
    public void init(Language language) throws IOException {
        if (getProblemId() == null) {
            throw new NullPointerException("Current problem is undefined");
        }
        File dir = new File(workspaceDir, getProblemId());
        if (dir.exists()) {
            throw new IllegalStateException("Directory is already exists!");
        }
        getProblemSettings().put(LANGUAGE_KEY, language.toString());
        initProblem(dir);
        createLanguageTemplate(dir, language);
    }

    private SettingsFile getProblemSettings() throws IOException {
        return new SettingsFile(new File(new File(getCurrentProblemPath(), PRIVATE_DIR_NAME), SETTINGS_FILE_NAME));
    }

    private File getCurrentProblemPath() throws IOException {
        return new File(workspaceDir, getProblemId());
    }

    private void initProblem(File dir) throws IOException {
        Problem problem = onlineJudgeSystem.getProblem(getProblemId());
        FileUtils.writeStringToFile(new File(dir, DESCRIPTION_FILE_NAME), problem.toString());
        createTestFiles(dir, problem);
    }

    private void createTestFiles(File dir, Problem problem) throws IOException {
        File tests = new File(dir, TESTS_DIR_NAME);
        int index = 0;
        for (SampleTest sampleTest : problem.getSampleTests()) {
            File testDir = new File(tests, String.valueOf(index));
            if (!testDir.exists()) {
                FileUtils.writeStringToFile(new File(testDir, INPUT_FILE_NAME), sampleTest.getInput() + "\r\n");
                FileUtils.writeStringToFile(new File(testDir, OUTPUT_FILE_NAME), sampleTest.getOutput() + "\r\n");
            }
            index++;
        }
    }

    private void createLanguageTemplate(File dir, Language language) throws IOException {
        LanguageSettings languageSettings = onlineJudgeSystem.getLanguageSettings(language);
        String resourceName =
                new StringBuilder(File.separator)
                        .append(TEMPLATES_DIR_NAME)
                        .append(File.separator)
                        .append(languageSettings.toString())
                        .append(File.separator)
                        .append(TEMPLATE_FILE_NAME)
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
            printWriter.append(line.replace(ID_KEY, String.valueOf(getProblemId())) + "\n");
        }
        printWriter.close();
        reader.close();
    }

    @Override
    public TestResult[] test() {
        Class solutionClass;
        String problemClassName = null;
        try {
            problemClassName= getProblemClassName();
            solutionClass = Class.forName(problemClassName);
        } catch (Exception e) {
            try {
                solutionClass = ClassLoadUtils.load(new File(workspaceDir, getProblemId()).getPath(), problemClassName);
            } catch (Exception e2) {
                return new TestResult[] {new TestResult("unknown", false, "Failed to load solution class: " + e2)};
            }
        }
        return test(solutionClass);
    }

    @Override
    public TestResult[] test(Class solution) {
        try {
            File[] tests = new File(new File(workspaceDir, getProblemId()), TESTS_DIR_NAME).listFiles();
            List<TestResult> results = new LinkedList<TestResult>();
            for (File test : tests) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                FileInputStream fileInputStream = new FileInputStream(new File(test, INPUT_FILE_NAME));
                Object solver = solution.getConstructor(InputStream.class, OutputStream.class).newInstance(fileInputStream, byteArrayOutputStream);
                solution.getMethod("run").invoke(solver);
                fileInputStream.close();
                byteArrayOutputStream.close();
                String output = byteArrayOutputStream.toString();
                String expectedOutput = FileUtils.readFileToString(new File(test, OUTPUT_FILE_NAME));
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
        String lang = getProblemSettings().get(LANGUAGE_KEY);
        if (lang == null) {
            throw new IllegalStateException("Settings file is broken");
        }
        Language language = Language.valueOf(lang);
        String source = FileUtils.readFileToString(new File(new File(workspaceDir, getProblemId()), getProblemFileName()));
        return onlineJudgeSystem.perform(new Commit(author, getProblemId(), language, source));
    }

    private String getProblemFileName() throws IOException {
        return getProblemClassName() + "." + onlineJudgeSystem.getLanguageSettings(Language.valueOf(getProblemSettings().get(LANGUAGE_KEY))).getExtension();
    }

    private String getProblemClassName() throws IOException {
        return PROBLEM_CLASS_NAME_PREFIX + getProblemId();
    }

    public String getProblemId() throws IOException {
        return settings.get(CURRENT_PROBLEM_ID_KEY);
    }

    public void setProblemId(String problemId) throws IOException {
        settings.put(CURRENT_PROBLEM_ID_KEY, problemId);
    }
}

