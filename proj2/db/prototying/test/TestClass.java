package db.prototying.test;

import static org.junit.Assert.*;

import org.junit.Test;
import db.prototying.Column;
import db.prototying.LinkedListDeque;
import db.prototying.List;
import db.prototying.NestedMap;

/**
 * Created by zitongyang on 2/24/17.
 */
public class TestClass {

    /**
     * This method creates the table that will be used
     * in other test method
     */
    public NestedMap[] CreateTable() {
        NestedMap[] result = (NestedMap[]) new NestedMap[2];
        String[] str1 = {"x", "y"};
        String[] str2 = {"x", "z"};
        List<String> name1 = new LinkedListDeque<>(str1);
        List<String> name2 = new LinkedListDeque<>(str2);
        NestedMap<Integer> nmap1 = new NestedMap<>(name1);
        NestedMap<Integer> nmap2 = new NestedMap<>(name2);
        Integer[] r11 = {2, 5};
        Integer[] r12 = {8, 3};
        Integer[] r13 = {13, 7};
        List<Integer> row11 = new LinkedListDeque<>(r11);
        List<Integer> row12 = new LinkedListDeque<>(r12);
        List<Integer> row13 = new LinkedListDeque<>(r13);
        nmap1.addNextRow(row11);
        nmap1.addNextRow(row12);
        nmap1.addNextRow(row13);
        Integer[] r21 = {2, 4};
        Integer[] r22 = {8, 9};
        Integer[] r23 = {10, 1};
        List<Integer> row21 = new LinkedListDeque<>(r21);
        List<Integer> row22 = new LinkedListDeque<>(r22);
        List<Integer> row23 = new LinkedListDeque<>(r23);
        nmap2.addNextRow(row21);
        nmap2.addNextRow(row22);
        nmap2.addNextRow(row23);
        result[0] = nmap1;
        result[1] = nmap2;
        return result;
    }

    /**
     * This method creates two table that have no column
     * in common.
     */
    public NestedMap[] CreatTableDistinct() {
        NestedMap[] result = (NestedMap[]) new NestedMap[2];
        String[] str1 = {"x", "y"};
        String[] str2 = {"z", "t"};
        List<String> name1 = new LinkedListDeque<>(str1);
        List<String> name2 = new LinkedListDeque<>(str2);
        NestedMap<Integer> nmap1 = new NestedMap<>(name1);
        NestedMap<Integer> nmap2 = new NestedMap<>(name2);
        Integer[] r11 = {2, 5};
        Integer[] r12 = {8, 3};
        Integer[] r13 = {13, 7};
        List<Integer> row11 = new LinkedListDeque<>(r11);
        List<Integer> row12 = new LinkedListDeque<>(r12);
        List<Integer> row13 = new LinkedListDeque<>(r13);
        nmap1.addNextRow(row11);
        nmap1.addNextRow(row12);
        nmap1.addNextRow(row13);
        Integer[] r21 = {2, 4};
        Integer[] r22 = {8, 9};
        Integer[] r23 = {10, 1};
        List<Integer> row21 = new LinkedListDeque<>(r21);
        List<Integer> row22 = new LinkedListDeque<>(r22);
        List<Integer> row23 = new LinkedListDeque<>(r23);
        nmap2.addNextRow(row21);
        nmap2.addNextRow(row22);
        nmap2.addNextRow(row23);
        result[0] = nmap1;
        result[1] = nmap2;
        return result;
    }

    @Test
    /** This test tests whether the concatenate method works
     * properly.
     */
    public void TestConcatenate() {
        String[] str1 = {"x", "y"};
        String[] str2 = {"x", "z"};
        LinkedListDeque<String> name1 = new LinkedListDeque<>(str1);
        LinkedListDeque<String> name2 = new LinkedListDeque<>(str2);
        List name3 = name1.concatenate(name2);
        name1.printDeque();
        name2.printDeque();
        name3.printDeque();
    }

    @Test
    /**
     * This test tests whether nestedMap works as expeced,
     * not using assertEquals but using a print method*/
    public void TestNestedMap() {
        List<String> names = new LinkedListDeque<>();
        names.addFirst("x");
        names.addFirst("y");
        names.addFirst("z");
        names.addFirst("t");

        NestedMap<Integer> testdMap = new NestedMap<>(names);
        Integer[] r1 = {1, 2, 3, 4};
        Integer[] r2 = {3, 6, 8, 1};
        Integer[] r3 = {34, 34, 64, 7};
        Integer[] r4 = {23, 6, 1, 67};
        List<Integer> row1 = new LinkedListDeque(r1);
        List<Integer> row2 = new LinkedListDeque(r2);
        List<Integer> row3 = new LinkedListDeque(r3);
        List<Integer> row4 = new LinkedListDeque(r4);
        testdMap.addNextRow(row1);
        testdMap.printMap();
        testdMap.addNextRow(row2);
        testdMap.printMap();
        testdMap.addNextRow(row3);
        testdMap.printMap();
        testdMap.addNextRow(row4);
        testdMap.printMap();
    }

    @Test
    /** This test tests whether the iterator function in
     * LinkedListDeque works properly.
     * Also, this test should test that Iterator is not
     * destructive on the original list*/
    public void DequeIterator() {
        List<Integer> testList = new LinkedListDeque<>();
        testList.addFirst(2);
        testList.addFirst(3);
        testList.addFirst(4);
        testList.addFirst(5);
        testList.addFirst(6);
        testList.addFirst(7);
        int i = 7;
        for (int num : testList) {
            assertEquals(i, num);
            i = i - 1;
        }
        i = 7;
        for (int num : testList) {
            assertEquals(i, num);
            i = i - 1;
        }
    }

    @Test
    /** This test tests the addNext and printEntry in
     * column class*/
    public void TestColumn() {
        Column<String> testColumn = new Column();
        assertEquals(0, testColumn.size());
        testColumn.addNext("a");
        testColumn.addNext("b");
        testColumn.addNext("c");
        testColumn.addNext("d");
        testColumn.addNext("a");
        testColumn.addNext("b");
        testColumn.addNext("c");
        testColumn.addNext("d");
        testColumn.addNext("a");
        testColumn.addNext("b");
        testColumn.addNext("c");
        testColumn.addNext("d");
        testColumn.addNext("a");
        testColumn.addNext("b");
        testColumn.addNext("c");
        testColumn.addNext("d");
        for (int j = 1; j < testColumn.size(); j++) {
            testColumn.printEntry(j);
        }


    }

    @Test
    /** This method tests whether the helper method helpColumnFilter
     * works properly.
     * In order to test this method, we need to set the joinColumnFilter
     * method temporarily to private.
     */
    public void TestFilterColumn() {
        NestedMap[] nmapArray = CreateTable();
        NestedMap nmap1 = nmapArray[0];
        NestedMap nmap2 = nmapArray[1];
        nmap1.printMap();
        System.out.println();
        nmap2.printMap();
        List<String> str = NestedMap.joinColumnFilter(nmap1, nmap2);

    }

    @Test
    /** This method tests whether the helper method joinRowFilter
     * works properly.
     * In order to test this method, we need to set the joinRowFilter
     * method temporarily to private.
     */
    public void TestFilterRow() {
        NestedMap[] nmapArray = CreateTable();
        NestedMap nmap1 = nmapArray[0];
        NestedMap nmap2 = nmapArray[1];
        List<String> str = NestedMap.joinColumnFilter(nmap1, nmap2);
        Column col1 = nmap1.get(str.get(0));
        Column col2 = nmap2.get(str.get(0));
        List<Integer[]> result = NestedMap.joinRowFilter(col1, col2);
    }

    @Test
    /** This method tests whether helper method joinCommonRowFilter
     * works as expected
     */
    public void TestJoinCommonRowFilter() {
        List<Integer[]> test1 = new LinkedListDeque<>();
        Integer[] arr1 = {1, 2};
        Integer[] arr2 = {1, 6};
        Integer[] arr3 = {1, 7};
        Integer[] arr4 = {1, 8};
        test1.addLast(arr1);
        test1.addLast(arr2);
        test1.addLast(arr3);
        test1.addLast(arr4);
        List<Integer[]> test2 = new LinkedListDeque<>();
        Integer[] arr5 = {1, 9};
        Integer[] arr6 = {1, 9};
        Integer[] arr7 = {1, 9};
        Integer[] arr8 = {1, 9};
        test2.addLast(arr5);
        test2.addLast(arr6);
        test2.addLast(arr7);
        test2.addLast(arr8);
        List<Integer[]> newList = NestedMap.joinCommonRowFilter(test1, test2);
    }

    @Test
    /** This method tests whether the CartesianProduct method
     * works properly.
     */
    public void TestCartesianProduct() {
        NestedMap[] nmapArray = CreatTableDistinct();
        NestedMap nmap1 = nmapArray[0];
        NestedMap nmap2 = nmapArray[1];
        NestedMap joined = NestedMap.CartesianProduct(nmap1, nmap2);
        joined.printMap();
    }

    @Test
    /** This method tests the join method */
    public void join() {
        NestedMap[] nmapArray = CreateTable();
        NestedMap nmap1 = nmapArray[0];
        NestedMap nmap2 = nmapArray[1];
        NestedMap result = NestedMap.join(nmap1,nmap2);
        System.out.print(result.printMap());
    }

    @Test
    public void testRemove(){
        List<Integer> a = new LinkedListDeque();
        a.addLast(0);
        a.addLast(1);
        a.addLast(2);
        a.addLast(3);
        a.addLast(4);
        a.addLast(5);
        a.remove(1);
        List<Integer> b = new LinkedListDeque();
        b.addLast(0);
        b.addLast(2);
        b.addLast(3);
        b.addLast(4);
        b.addLast(5);
        assertEquals(b, a);

    }

}
