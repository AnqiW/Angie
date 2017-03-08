package db;


import static org.junit.Assert.*;
import db.prototying.ClassChecker.*;
import db.prototying.LinkedListDeque;
import db.prototying.List;
import db.prototying.Readers.ReaderClass;
import db.prototying.Readers.Reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;


/**
 * Created by zitongyang on 2/28/17.
 */
public class TestDatabase {

    /**
     * This method tests whether create table using command of the
     * first kind works properly.
     */
    @Test
    public void TestEvalCreate() {
        Database d = new Database();
        Parse.eval("create table T1 (x        int, y     int)", d);
        Parse.eval("select * from T1, T2", d);

    }

    @Test
    public void TestInsert() {
        Database d = new Database();
        Parse.eval("create table T1 (x        int, y int)", d);
        Parse.eval("insert into T1 values 2, 5", d);
        Parse.eval("insert into T1 values 2, 5", d);
        Parse.eval("insert into T1 values 2, 5", d);
        Parse.eval("insert into T1 values 2, 5", d);
        System.out.print(Parse.eval("print T1", d));
    }

    @Test
    public void TestJoin() {
        Database d = new Database();
        Parse.eval("create table T1 (x       int, y int)", d);
        Parse.eval("insert into T1 values 2, 5", d);
        Parse.eval("insert into T1 values 8, 3", d);
        Parse.eval("insert into T1 values 13, 7", d);
        Parse.eval("create table T2 (x int, t int)", d);
        Parse.eval("insert into T2 values 2, 4", d);
        Parse.eval("insert into T2 values 8, 9", d);
        Parse.eval("insert into T2 values 10, 1", d);
        System.out.print(Parse.eval("select * from T1,T2", d));
    }

    @Test
    public void TestLoad() {
        Database d = new Database();
        Parse.eval("load t1", d);
        System.out.print(Parse.eval("print t1", d));
    }


    /**
     * This test method tests how Parse class can read commands
     */
    @Test
    public void TestSelectRead() {
        Database d = new Database();
        Parse.eval("load t2", d);
        Parse.eval("select x + y as a, x*z as d from t2 where x>2 and b <c and h>3", d);
    }

    /**
     * This test method tests whether ReadColumExpr works correctly
     */
    @Test
    public void TestReadColumnExpr() {
        Reader testReader = new ReaderClass();
        String testExpr1 = "col1 + col2 as alias";
        String testExpr2 = "col1";
        String[] expected1 = {"col1", "col2", "+", "alias"};
        String[] expected2 = {"col1"};
        List<String> e1 = new LinkedListDeque<>(expected1);
        List<String> e2 = new LinkedListDeque<>(expected2);
        List actual1 = testReader.readColumExpr(testExpr1);
        List actual2 = testReader.readColumExpr(testExpr2);
    }

    @Test
    public void TestDropTable() {
        Database d = new Database();
        Parse.eval("create table T7 (String a, String b, String c)", d);
        Parse.eval("load t1", d);
        Parse.eval("drop table t1", d);
        Parse.eval("load t1", d);
        Parse.eval("drop table t1", d);
        Parse.eval("load t1", d);
        Parse.eval("print T7", d);
    }


    @Test
    public void TestLoadMalformed() {
        Database d = new Database();
        Parse.eval("load t7", d);
    }

    @Test
    public void TestCommand() {
        Database d = new Database();
        Parse.eval("Zitong !", d);
    }

    @Test
    public void TestReadCommaSplit() {
        Reader r = new ReaderClass();
        String result = r.readType("x int");
    }

    @Test
    public void TestFloat() {
        Database d = new Database();
        Parse.eval("create table T1 (x        float, y float)", d);
        Parse.eval("insert into T1 values 2.0000, 5.0000", d);
        Parse.eval("insert into T1 values 2.21312, 5.123123", d);
        Parse.eval("insert into T1 values 2, 5", d);
        System.out.print(Parse.eval("print T1", d));
    }

    @Test
    public void TestType(){
        Float f = new Float(2.2);
        ClassChecker checker = new ClassChecker();
        assertTrue(checker.isFloat("2.2"));
        assertTrue(checker.isFloat("2.2"));
        assertTrue(checker.isFloat("2.2"));
        assertTrue(checker.isFloat("2.2"));
        assertTrue(checker.isFloat("2.2"));

    }

    @Test
    public void TestColExpression() {
        Database d = new Database();
        Parse.eval("load t4", d);
        System.out.print(Parse.eval("select a * b as c from t4", d));

    }

    @Test
    public void GeneralTest1(){
        Database d = new Database();
        Parse.eval("load t4", d);
        System.out.print(Parse.eval("select a from t4", d));
        Parse.eval("load t2", d);
        System.out.print(Parse.eval("select * from t2, t4", d));
        System.out.print(Parse.eval("select a + b as c from t4", d));
    }

    @Test
    public void TestReorderColumns() {
        Database d = new Database();
        Parse.eval("load t1", d);
        System.out.print(Parse.eval("select b,a from t1", d));
    }

    @Test
    public void SelectAllAssortedBasic1() {
        Database d = new Database();
        Parse.eval("load fans", d);
        System.out.print(Parse.eval("select * from fans", d));
    }

    @Test
    public void SelectExpressionsBasic(){
        Database d = new Database();
        Parse.eval("load t1", d);
        System.out.print(Parse.eval("select a+b as c from t1", d));
    }

    @Test
    public void SelectAllAssortedBasic2() {
        Database d = new Database();
        Parse.eval("create table fans (Lastname string,Firstname string,TeamName string)", d);
        Parse.eval("insert into fans values 'Lee','Maurice','Mets'", d);
        System.out.print(Parse.eval("print fans", d));
    }

    @Test
    public void CreateSelectBasic1(){
        Database d = new Database();
        Parse.eval("load t2", d);
        Parse.eval("create table tablename as select x, z from t2", d);
        System.out.print(Parse.eval("print tablename", d));
    }

    @Test
    public void CreateSelectBasic2(){
        Database d = new Database();
        Parse.eval("load records", d);
        Parse.eval("create table t3 as select TeamName, Wins + Losses as Games from records", d);
        System.out.print(Parse.eval("print t3", d));
    }

    @Test
    public void TestConditionRead(){
        Reader rd = new ReaderClass();
        String[] conds = rd.readAndSplit("x>2");
        for (String cond:conds){
            String[] test;
            test = rd.readCondition(cond);
            System.out.println(test[0]);
            System.out.println(test[1]);
            System.out.println(test[2]);
        }
    }

    @Test
    public void TestSelect(){
        Database d = new Database();
        Parse.eval("load t2", d);
        System.out.print(Parse.eval("select x+z as m from t2",d));
    }

    @Test
    public void TestCondition(){
        Database d = new Database();
        Parse.eval("load t2", d);
        System.out.println(Parse.eval("select x,z from t2 where x==z", d));
    }

}





