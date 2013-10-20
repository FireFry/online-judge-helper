package com.firefrydev.ojh.timus;

import com.firefrydev.ojh.core.Problem;
import com.firefrydev.ojh.core.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ListIterator;

public class TimusProblemLoader {

    public static Problem loadProblem(String id) throws IOException {
        String url = String.format("http://acm.timus.ru/print.aspx?space=1&num=%s", id);
        Document document = Jsoup.connect(url).get();
        String description = document.getElementsByClass("problem_par_normal").text();
        ListIterator<Element> tests = document.getElementsByClass("sample").first().getElementsByClass("intable").listIterator();
        Problem.Builder builder = Problem.builder().setId(id).setDescription(description);
        while (tests.hasNext()) {
            builder.addTest(new Test(tests.next().text() + '\n', tests.next().text() + '\n'));
        }
        return builder.build();
    }

}
