/**
 * tokenizer : this program demonstrates the process of tokenization
 */
public class tokenizer {
    static int start = 0, current = 0, line = 0;
    // test variables below...
    static String identifier_source = "_hello_v1_world";
    static String string_source = "\"hello\" world";
    static String comment_source = "// This is a comment";
    static String expresion_source = "((2 + 2) * 8)/16";
    static String numeric_source = "1234";
    static String alphaNumeric_source = "alpha123Num123 6ix96ix9";
    static String source = comment_source;

    public static void main(String[] args) {

        while (!isAtEnd()) {
            start = current;
            // if start is not initialised to current , then start remiains 0 at zero and
            // hence the tokenizer adds the tokens incrementally instead of individually
            scanTokens();
        }

    }

    /*
     * returns true if the end of the source is reached
     */
    static boolean isAtEnd() {
        if (current >= source.length()) {
            return true;
        } else
            return false;

    }

    /*
     * Scans the tokens from the source
     */
    static void scanTokens() {
        char c = advance();
        switch (c) {
            case '(':
                System.out.println("(");
                break;

            case ')':
                System.out.println(")");
                break;

            case '{':
                System.out.println("{");
                break;

            case '}':
                System.out.println("}");
                break;

            case ',':
                System.out.println(",");
                break;

            case '.':
                System.out.println(".");
                break;

            case '-':
                System.out.println("-");
                break;

            case '+':
                System.out.println("+");
                break;

            case ';':
                System.out.println(";");
                break;

            case '*':
                System.out.println("*");
                break;

            case '!':
                // as the '!' is a unary operator, it can be followed by '=' , hence match()
                // checks for expected character
                if (match('=')) {
                    System.out.println("!=");
                } else
                    System.out.println("!");
                break;

            case '=':
                if (match('=')) {
                    System.out.println("==");
                } else
                    System.out.println("=");
                break;

            case '<':
                if (match('=')) {
                    System.out.println("<=");
                } else
                    System.out.println("<");
                break;

            case '>':
                if (match('=')) {
                    System.out.println(">=");
                } else
                    System.out.println(">");
                break;

            case '/':// '/' when used individually,then used as division opearator, else it is used
                     // as a sinngle line slash comment
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                } else {
                    System.out.println("/");
                }
                break;

            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                line++;
                break;

            case '"':// checks for string
                string();
                break;
            default:
                // checks for strings which start with number
                if (!isDigit(peek())) {
                    identifier();

                } // checks for numbers
                else if (isDigit(c)) {
                    number();
                }
                // checks for alphabets
                else if (isAlpha(c)) {
                    identifier();
                } else {
                    System.out.print("Unexpected character.");
                }
                break;
        }
    }

    static char advance() {
        // consumes and increments current
        return source.charAt(current++);
    }

    static boolean match(char expected) {

        if (isAtEnd())
            return false;
        // checks if expected is equal to current character
        if (source.charAt(current) != expected)
            return false;
        advance();
        return true;

    }

    static void number() {
        // checks the next character is a digit and advances the current pointer

        while (isDigit(peek()) && !isAtEnd()) {
            advance();
        }

        // checks for decimal point in the number

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek()))
                advance();
        }
        System.out.println(source.substring(start, current));
    }

    static void string() {
        // checks for the end of the string
        while (peek() != '"' && !isAtEnd()) {
            // checks for new line character
            if (peek() == '\n')
                line++;
            advance();

        }
        // if '"' is not found then it is an unterminated string
        if (isAtEnd()) {
            System.out.println("Unterminated string.");
            return;
        }
        // consumes the closing '"' character

        advance();
        System.out.println(source.substring(start + 1, current - 1));
    }

    static void identifier() {

        // checks for the end of the identifier
        while (isAlphaNumeric(peek()) && !isAtEnd()) {
            advance();
        }
        System.out.println(source.substring(start, current));
    }

    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';

    }

    static boolean isAlpha(char c) {
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_') {
            return true;
        } else
            return false;

    }

    static boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    static char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    static char peekNext() {
        if (current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }
}