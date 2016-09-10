package hackathon.neko.service;

import hackathon.neko.model.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class HtmlService {

    public List<String> extractFromHTML() {
        // WebサイトからHTMLを取得する
        try {
            URL url = new URL("https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array");
            URLConnection uc = url.openConnection();

            BufferedInputStream bis = new BufferedInputStream(uc.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(bis));

            String line;
            StringBuffer originalHTML = new StringBuffer();
            while ((line = br.readLine()) != null) {
                originalHTML.append(line);
            }
            return extractSection(originalHTML);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTMLから必要な部分だけ取り出す.
     * @param originalHTML
     * @return
     */
    private List<String> extractSection(StringBuffer originalHTML) {
        String regex = "<p><strong>(.*?)</pre>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(originalHTML);
        List<String> extractedSection = new ArrayList<String>();
        while (matcher.find()) {
            extractedSection.add(matcher.group().replaceAll("\t", "").replaceAll("&nbsp;", ""));
        }
        return extractedSection;
    }

    private String extractContent(String parseTargetStr, String checkPattern) {
        Pattern pattern = Pattern.compile(checkPattern, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(parseTargetStr);
        String matched = null;
        if (matcher.find()) {
            matched = matcher.group(1).replaceAll(";", ";<br>");
        }
        return matched;
    }

    public List<Data> convertToData(List<String> rawData) {
        return rawData.stream()
        .map(d -> {
            String title = extractContent(d, "<p><strong>(.*?)</strong></p>");
            String code = extractContent(d, "<pre class=\"brush: js\">(.*?)//");
            String result = extractContent(d, "// (.*?)</pre>");
            return StringUtils.isEmpty(title)
                    || StringUtils.isEmpty(code)
                    || StringUtils.isEmpty(result) ?
                    null: new Data(title, code, result);
        }).filter(r -> r != null && !r.getResult().contains("//")).collect(Collectors.toList());
    }
}
