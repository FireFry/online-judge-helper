package com.firefrydev.ojh.timus;

import com.firefrydev.ojh.core.Commit;
import com.firefrydev.ojh.core.Verdict;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ListIterator;

public class TimusVerifier {

    public static String send(Commit commit) throws IOException {
        int languageId = LanguageSettings.valueOf(commit.getLanguage()).getId();
        Document doc = Jsoup.connect("http://acm.timus.ru/submit.aspx")
                        .header("Accept-Language", "ru-RU,ru;")
                        .data("Action", "submit")
                        .data("SpaceID", "1")
                        .data("JudgeID", commit.getAuthorId())
                        .data("Language", String.valueOf(languageId))
                        .data("ProblemNum", commit.getProblemId())
                        .data("Source", commit.getSourceCode())
                        .post();
        Elements tables = doc.getElementsByClass("status");
        Elements rows = tables.first().getElementsByTag("tr");
        ListIterator<Element> list = rows.listIterator();
        String commitId = null;
        while (list.hasNext() && commitId == null) {
            Element next = list.next();
            if (next.getElementsByAttributeValue("href", "author.aspx?id=" + commit.getAuthorId()).size() > 0) {
                commitId = next.getElementsByClass("id").text();
            }
        }
        return commitId;
    }

    public static Verdict loadVerdict(Commit commit, String commitId) throws IOException {
        Document doc = Jsoup.connect("http://acm.timus.ru/status.aspx")
                        .header("space", "1")
                        .header("num", String.valueOf(commit.getProblemId()))
                        .header("author", String.valueOf(commit.getAuthorId()))
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
            Elements accepted = row.getElementsByClass("verdict_ac");
            if (accepted.size() > 0) {
                return Verdict.accepted(commit.getProblemId());
            }
            Elements rejected = row.getElementsByClass("verdict_rj");
            if (rejected.size() > 0) {
                return Verdict.rejected(commit.getProblemId(), rejected.text());
            }
        }
        return null;
    }

}
