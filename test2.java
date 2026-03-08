public class test2 {

    static final int REPEATS = 7;

    public static void main(String[] args) {

        System.out.println("===== LOCK / UNLOCK Complexity Test =====\n");

        testLockUnlock(10);        // small
        testLockUnlock(1000);      // medium
        testLockUnlock(1000000);   // large

        System.out.println("\n===== TEST FINISHED =====");
    }

    static void testLockUnlock(int N) {

        long total = 0;

        // prepare accounts
        BudgetSystem.balanceByAccount.clear();
        BudgetSystem.lockedAccounts.clear();

        for (int i = 0; i < N; i++) {
            BudgetSystem.balanceByAccount.put("A" + i, 1000);
        }

        for (int r = 0; r < REPEATS; r++) {

            long start = System.nanoTime();

            for (int i = 0; i < N; i++) {
                BudgetSystem.lock("A" + i);
                BudgetSystem.unlock("A" + i);
            }

            long end = System.nanoTime();

            total += (end - start);
        }

        System.out.println("Size: " + N +
                " → lock/unlock avg time (HashSet O(1) avg): "
                + (total / REPEATS) + " ns");
    }
}