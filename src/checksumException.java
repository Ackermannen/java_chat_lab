/**
 * Datakommunikation och Internet (Java) - 57203HT17
 * Autumn 2017
 * Assignment 2
 * Date: 2017-10-21
 * Class: checksumException
 * Description: throwable Exception for faulty checksum.
 * @author Wilhelm Ackermann - id16wan
 * @author David Eriksson    - id16den
 */
public class checksumException extends Exception {
    public checksumException(String s) {
        super(s);
    }
}
