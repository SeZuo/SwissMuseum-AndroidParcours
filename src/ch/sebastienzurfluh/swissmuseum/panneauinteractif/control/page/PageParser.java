package ch.sebastienzurfluh.swissmuseum.panneauinteractif.control.page;

/**
 * Created by sebe on 7/31/13.
 */
import java.util.LinkedList;

public class PageParser {
    private static final String IMG_BALISE_START = "[img]";
    private static final String IMG_BALISE_END = "[/img]";

    public LinkedList<PageToken> parse(String text) {
        System.out.println("TextParser: parsing...");
        return parseResources(text);
    }

    private LinkedList<PageToken> parseResources(String parsing) {
        LinkedList<PageToken> tokenList = new LinkedList<PageToken>();
        StringBuilder parsed = new StringBuilder();

        while (!parsing.isEmpty()) {
            int imgDefBegin = parsing.indexOf(IMG_BALISE_START);
            int imgDefEnd = parsing.indexOf(IMG_BALISE_END);

            if(imgDefBegin == -1 || imgDefEnd == -1) {
                parsed.append(parsing);
                break;
            } else {
                parsed.append(parsing.substring(0, imgDefBegin));

                PageToken textParsedSoFar = new PageToken(parsed.toString());

                tokenList.add(textParsedSoFar);
                parsed = new StringBuilder();

                DataReference neededResource = new DataReference(
                        DataType.RESOURCE,
                        Integer.parseInt(
                                parsing.substring(
                                        imgDefBegin + IMG_BALISE_START.length(),
                                        imgDefEnd)));

                tokenList.add(new PageToken(new ResourceWidget(
                        neededResource,
                        pageChangeEventBus,
                        model)));


                parsing = parsing.substring(imgDefEnd + IMG_BALISE_END.length());
            }
        }

        // Finish to empty the string builder.
        if (parsed.length() > 0)
            tokenList.add(new PageToken(parsed.toString()));

        return tokenList;
    }
}