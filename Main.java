import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

class Main {

    // Level 1
    static IntStream twinPrimes(int n) {
        return IntStream.rangeClosed(2, n)
            .filter(x -> isPrime(x) && 
                    (isPrime(x + 2) || isPrime(x - 2)));
    }

    private static boolean isPrime(int n) {
        return n > 1 && IntStream.range(2, (int) Math.sqrt(n) + 1)
            .noneMatch(x -> n % x == 0);
    }
    
    // Level 2
    static String reverse(String str) {
        return Stream.<String>of(str.split(""))
            .reduce("", (x, y) -> y + x);
    }

    // Level 3
    static long countRepeats(List<Integer> list) { // ends with count()?
        return IntStream.range(1,list.size())
            .filter(x -> checker(list, x)).count();
    }

    private static boolean checker(List<Integer> list, int x) {
        if (x == list.size() - 1 && list.get(x) == list.get(x - 1)) {
            return true;
        } else if (list.get(x) == list.get(x - 1) && list.get(x) != list.get(x + 1)) {
            return true;
        }
        return false;
    }
    
    // Level 4
    static UnaryOperator<List<Integer>> generateRule() {      
        return list -> IntStream.range(0, list.size())
                    .map(x -> { 
                        if (list.get(x) == 1) { // alive to dead
                            return 0;
                        } else if ((x == list.size() - 1)) { // last in the list
                            return (list.get(x - 1) == 1) ? 1 : 0;
                        } else if (x == 0) { // first in the list
                            return (list.get(x + 1) == 1) ? 1 : 0; 
                        } else if ((list.get(x + 1) == 1 && list.get(x - 1) == 0)
                                || (list.get(x - 1) == 1 && list.get(x + 1) == 0)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }).boxed().collect(Collectors.toUnmodifiableList());
    }

    static Stream<String> gameOfLife(List<Integer> list, UnaryOperator<List<Integer>> rule, int n) {
        return Stream.iterate(list, rule).limit(n).map(x -> generator(x));
    }

    private static String generator(List<Integer> list) {
        return list.stream().map(x -> (x == 0) ? " " : "*").collect(Collectors.joining());
    }
}



