package nsu.fit.jasic;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        InputStream input = new FileInputStream(new File(args[0]));

        ParserDescriptor parser = Parboiled.createParser(ParserDescriptor.class);
        ParseRunner<Object> parserRunner = new ReportingParseRunner<Object>(parser.jasicRunnable());
        String input = "let a=\"test\"+\"a\"+\"a\"";
        ParsingResult<Object> parsingResult = parserRunner.run(input);
        System.out.println(ParseTreeUtils.printNodeTree(parsingResult));
    }
}
