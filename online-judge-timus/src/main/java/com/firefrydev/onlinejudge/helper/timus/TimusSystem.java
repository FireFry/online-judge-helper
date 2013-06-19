package com.firefrydev.onlinejudge.helper.timus;

import com.firefrydev.onlinejudge.helper.core.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class TimusSystem implements OnlineJudgeSystem {
    private static final String LOAD_URL_PREFIX = "http://acm.timus.ru/print.aspx?space=1&num=";
    private static final String CHECK_URL = "http://acm.timus.ru/status.aspx";

    private static final HashMap<Language, LanguageSettings> LANGUAGE_SETTINGS = new HashMap<Language, LanguageSettings>() {{
        put(Language.JAVA, new LanguageSettings("12", "java", "java"));
        put(Language.PYTHON27, new LanguageSettings("16", "python", "py"));
    }};
    public static final int SLEEP_INTERVAL = 500;

    @Override
    public Problem getProblem(String id) throws IOException {
        Document doc =
                Jsoup.connect(LOAD_URL_PREFIX + id)
                            .header("Accept-Language", "ru-RU,ru;")
                            .get();
        String title = doc.getElementsByClass("problem_title").first().text();
        String name = title.substring(title.indexOf('.') + 2);
        String taskDescription = doc.getElementsByClass("problem_par_normal").text();
        ListIterator<Element> samples = doc.getElementsByClass("sample").first().getElementsByClass("intable").listIterator();
        List<SampleTest> tests = new LinkedList<SampleTest>();
        while (samples.hasNext()) {
            tests.add(new SampleTest(samples.next().text(), samples.next().text()));
        }
        return new Problem(id, name, taskDescription, "", "", tests);
    }

    @Override
    public CommitResult perform(Commit commit) throws IOException, InterruptedException {
        Document doc =
                Jsoup.connect("http://acm.timus.ru/submit.aspx")
                            .header("Accept-Language", "ru-RU,ru;")
                            .data("Action", "submit")
                            .data("SpaceID", "1")
                            .data("JudgeID", commit.getAuthor().getPassword())
                            .data("Language", getLanguageSettings(commit.getLanguage()).getId())
                            .data("ProblemNum", commit.getProblemId())
                            .data("Source", commit.getSource())
                            .post();
        Elements tables = doc.getElementsByClass("status");
        Elements rows = tables.first().getElementsByTag("tr");
        ListIterator<Element> list = rows.listIterator();
        String commitId = null;
        while (list.hasNext() && commitId == null) {
            Element next = list.next();
            if (next.getElementsByAttributeValue("href", "author.aspx?id=" + commit.getAuthor().getId()).size() > 0) {
                commitId = next.getElementsByClass("id").text();
            }
        }
        CommitResult result = null;
        while (result == null) {
            result = checkForResult(commit, commitId);
            if (result == null) {
                Thread.sleep(SLEEP_INTERVAL);
            }
        }
        return result;
    }

    private CommitResult checkForResult(Commit commit, String commitId) throws IOException {
        Document doc =
                Jsoup.connect(CHECK_URL)
                            .header("space", "1")
                            .header("num", String.valueOf(commit.getProblemId()))
                            .header("author", String.valueOf(commit.getAuthor().getId()))
                            .get();
        ListIterator<Element> iter = doc.getElementsByClass("id").listIterator();
        Element row = null;
        while (iter.hasNext() && row == null) {
            Element next = iter.next();
            if (next.text().equals(String.valueOf(commitId))) {
                row = next.parent();
            }
        }
        if (row != null) {
            Elements verdict;
            verdict = row.getElementsByClass("verdict_rj");
            boolean isAccepted = false;
            if (verdict.size() == 0) {
                verdict = row.getElementsByClass("verdict_ac");
                isAccepted = true;
            }
            if (verdict.size() > 0) {
                return new CommitResult(isAccepted, verdict.text());
            }
        }
        return null;
    }

    @Override
    public LanguageSettings getLanguageSettings(Language language) {
        return LANGUAGE_SETTINGS.get(language);
    }
}
