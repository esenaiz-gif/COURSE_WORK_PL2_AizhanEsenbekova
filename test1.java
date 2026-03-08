public class test1 {

    public static void main(String[] args) {

        System.out.println("===== TOP CATEGORIES Complexity Test =====\n");

        runTest(10);
        runTest(1000);
        runTest(10000);

        System.out.println("\n===== TEST FINISHED =====");
    }

    static void runTest(int size) {

        // clear previous data
        BudgetSystem.categorySpend.clear();

        // fill categories
        for (int i = 0; i < size; i++) {
            BudgetSystem.categorySpend.put("Category" + i, i);
        }

        long start = System.nanoTime();

        BudgetSystem.topCategories(5);

        long end = System.nanoTime();

        long time = end - start;

        System.out.println("Size: " + size + " → " + time + " ns");
    }
}