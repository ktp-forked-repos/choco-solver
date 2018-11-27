/**
 * This file is part of choco-solver, http://choco-solver.org/
 *
 * Copyright (c) 2018, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.solver.variables.view;

import org.chocosolver.solver.Cause;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import org.chocosolver.util.iterators.DisposableRangeIterator;
import org.chocosolver.util.iterators.DisposableValueIterator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * <p>
 * Project: choco-solver.
 *
 * @author Charles Prud'homme
 * @since 26/11/2018.
 */
public class EqViewTest {

    Model model;
    IntVar x;
    BoolVar b;

    @BeforeMethod(alwaysRun = true)
    public void before(){
        model = new Model();
        x = model.intVar("x", 0,5);
        b = new EqView(x, 3);
    }

    @Test(groups = "1s")
    public void testMonitorDelta() {
    }

    @Test(groups = "1s")
    public void testGetBooleanValueU() {
        Assert.assertEquals(b.getBooleanValue(), ESat.UNDEFINED);
    }

    @Test(groups = "1s")
    public void testGetBooleanValueT() throws ContradictionException {
        Assert.assertTrue(x.instantiateTo(3, Cause.Null));
        Assert.assertEquals(b.getBooleanValue(), ESat.TRUE);
    }

    @Test(groups = "1s")
    public void testGetBooleanValueF() throws ContradictionException {
        Assert.assertTrue(x.removeValue(3, Cause.Null));
        Assert.assertEquals(b.getBooleanValue(), ESat.FALSE);
    }

    @Test(groups = "1s")
    public void testSetToTrue() throws ContradictionException{
        Assert.assertTrue(b.setToTrue(Cause.Null));
        Assert.assertTrue(x.isInstantiatedTo(3));
    }

    @Test(groups = "1s")
    public void testSetToFalse() throws ContradictionException {
        Assert.assertTrue(b.setToFalse(Cause.Null));
        Assert.assertFalse(x.contains(3));
    }

    @Test(groups = "1s")
    public void testDoInstantiateVar0() throws ContradictionException {
        Assert.assertTrue(b.instantiateTo(0, Cause.Null));
        Assert.assertFalse(x.contains(3));
    }

    @Test(groups = "1s")
    public void testDoInstantiateVar1() throws ContradictionException {
        Assert.assertTrue(b.instantiateTo(1, Cause.Null));
        Assert.assertTrue(x.isInstantiatedTo(3));
    }

    @Test(groups = "1s", expectedExceptions = ContradictionException.class)
    public void testDoInstantiateVar2() throws ContradictionException {
        Assert.assertFalse(b.instantiateTo(2, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoUpdateLowerBoundOfVar11() throws ContradictionException {
        Assert.assertFalse(b.updateLowerBound(-1, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoUpdateLowerBoundOfVar0() throws ContradictionException {
        Assert.assertFalse(b.updateLowerBound(0, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoUpdateLowerBoundOfVar1() throws ContradictionException {
        Assert.assertTrue(b.updateLowerBound(1, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertTrue(x.isInstantiated());
    }

    @Test(groups = "1s", expectedExceptions = ContradictionException.class)
    public void testDoUpdateLowerBoundOfVar2() throws ContradictionException {
        Assert.assertFalse(b.updateLowerBound(2, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s", expectedExceptions = ContradictionException.class)
    public void testDoUpdateUpperBoundOfVar11() throws ContradictionException {
        Assert.assertFalse(b.updateUpperBound(-1, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoUpdateUpperBoundOfVar0() throws ContradictionException {
        Assert.assertTrue(b.updateUpperBound(0, Cause.Null));
        Assert.assertFalse(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoUpdateUpperBoundOfVar1() throws ContradictionException {
        Assert.assertFalse(b.updateUpperBound(1, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoUpdateUpperBoundOfVar2() throws ContradictionException {
        Assert.assertFalse(b.updateUpperBound(2, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoRemoveValueFromVar11() throws ContradictionException {
        Assert.assertFalse(b.removeValue(-1, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoRemoveValueFromVar0() throws ContradictionException {
        Assert.assertTrue(b.removeValue(0, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertTrue(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoRemoveValueFromVar1() throws ContradictionException {
        Assert.assertTrue(b.removeValue(1, Cause.Null));
        Assert.assertFalse(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoRemoveValueFromVar2() throws ContradictionException {
        Assert.assertFalse(b.removeValue(2, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s", expectedExceptions = ContradictionException.class)
    public void testDoRemoveIntervalFromVar11() throws Exception {
        Assert.assertFalse(b.removeInterval(-1, 2, Cause.Null));
    }

    @Test(groups = "1s", expectedExceptions = ContradictionException.class)
    public void testDoRemoveIntervalFromVar01() throws Exception {
        Assert.assertFalse(b.removeInterval(0, 1, Cause.Null));
    }

    @Test(groups = "1s")
    public void testDoRemoveIntervalFromVar10() throws Exception {
        Assert.assertTrue(b.removeInterval(-1, 0, Cause.Null));
        Assert.assertTrue(x.contains(3));
        Assert.assertTrue(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testDoRemoveIntervalFromVar12() throws Exception {
        Assert.assertTrue(b.removeInterval(1, 2, Cause.Null));
        Assert.assertFalse(x.contains(3));
        Assert.assertFalse(x.isInstantiated());
    }

    @Test(groups = "1s")
    public void testContains11() {
        Assert.assertFalse(b.contains(-1));
    }

    @Test(groups = "1s")
    public void testContains0() throws ContradictionException {
        Assert.assertTrue(b.contains(0));
        x.instantiateTo(3, Cause.Null);
        Assert.assertFalse(b.contains(0));
    }

    @Test(groups = "1s")
    public void testContains1() throws ContradictionException {
        Assert.assertTrue(b.contains(1));
        x.removeValue(3, Cause.Null);
        Assert.assertFalse(b.contains(1));
    }

    @Test(groups = "1s")
    public void testContains2() {
        Assert.assertFalse(b.contains(2));
    }

    @Test(groups = "1s")
    public void testIsInstantiatedTo0() throws ContradictionException {
        Assert.assertFalse(b.isInstantiatedTo(0));
        x.removeValue(3, Cause.Null);
        Assert.assertTrue(b.isInstantiatedTo(0));
    }

    @Test(groups = "1s")
    public void testIsInstantiatedTo1() throws ContradictionException {
        Assert.assertFalse(b.isInstantiatedTo(1));
        x.instantiateTo(3, Cause.Null);
        Assert.assertTrue(b.isInstantiatedTo(1));
    }

    @Test(groups = "1s")
    public void testGetLB0() throws Exception {
        Assert.assertEquals(b.getLB(), 0);
        x.instantiateTo(3, Cause.Null);
        Assert.assertEquals(b.getLB(), 1);
    }

    @Test(groups = "1s")
    public void testGetLB1() throws Exception {
        Assert.assertEquals(b.getLB(), 0);
        x.removeValue(3, Cause.Null);
        Assert.assertEquals(b.getLB(), 0);
    }

    @Test(groups = "1s")
    public void testGetUB0() throws ContradictionException {
        Assert.assertEquals(b.getUB(), 1);
        x.instantiateTo(3, Cause.Null);
        Assert.assertEquals(b.getUB(), 1);
    }

    @Test(groups = "1s")
    public void testGetUB1() throws ContradictionException {
        Assert.assertEquals(b.getUB(), 1);
        x.removeValue(3, Cause.Null);
        Assert.assertEquals(b.getUB(), 0);
    }

    @Test(groups = "1s")
    public void testNextValue1() {
        Assert.assertEquals(b.nextValue(-1), 0);
        Assert.assertEquals(b.nextValue(0), 1);
        Assert.assertEquals(b.nextValue(1), Integer.MAX_VALUE);
    }

    @Test(groups = "1s")
    public void testNextValue2() throws ContradictionException {
        x.instantiateTo(3, Cause.Null);
        Assert.assertEquals(b.nextValue(-1), 1);
        Assert.assertEquals(b.nextValue(0), 1);
        Assert.assertEquals(b.nextValue(1), Integer.MAX_VALUE);
    }

    @Test(groups = "1s")
    public void testNextValue3() throws ContradictionException {
        x.removeValue(3, Cause.Null);
        Assert.assertEquals(b.nextValue(-1), 0);
        Assert.assertEquals(b.nextValue(0), Integer.MAX_VALUE);
        Assert.assertEquals(b.nextValue(1), Integer.MAX_VALUE);
    }

    @Test(groups = "1s")
    public void testNextValueOut1() {
        Assert.assertEquals(b.nextValueOut(-2), -1);
        Assert.assertEquals(b.nextValueOut(-1), 2);
        Assert.assertEquals(b.nextValueOut(0), 2);
        Assert.assertEquals(b.nextValueOut(1), 2);
        Assert.assertEquals(b.nextValueOut(2), 3);
    }

    @Test(groups = "1s")
    public void testNextValueOut2() throws ContradictionException {
        x.instantiateTo(3, Cause.Null);
        Assert.assertEquals(b.nextValueOut(-2), -1);
        Assert.assertEquals(b.nextValueOut(-1), 0);
        Assert.assertEquals(b.nextValueOut(0), 2);
        Assert.assertEquals(b.nextValueOut(1), 2);
        Assert.assertEquals(b.nextValueOut(2), 3);
    }

    @Test(groups = "1s")
    public void testNextValueOut3() throws ContradictionException {
        x.removeValue(3, Cause.Null);
        Assert.assertEquals(b.nextValueOut(-2), -1);
        Assert.assertEquals(b.nextValueOut(-1), 1);
        Assert.assertEquals(b.nextValueOut(0), 1);
        Assert.assertEquals(b.nextValueOut(1), 2);
        Assert.assertEquals(b.nextValueOut(2), 3);
    }

    @Test(groups = "1s")
    public void testPreviousValue1() {
        Assert.assertEquals(b.previousValue(10), 1);
        Assert.assertEquals(b.previousValue(2), 1);
        Assert.assertEquals(b.previousValue(1), 0);
        Assert.assertEquals(b.previousValue(0), Integer.MIN_VALUE);
    }

    @Test(groups = "1s")
    public void testPreviousValue2() throws ContradictionException {
        x.instantiateTo(3, Cause.Null);
        Assert.assertEquals(b.previousValue(10), 1);
        Assert.assertEquals(b.previousValue(2), 1);
        Assert.assertEquals(b.previousValue(1), Integer.MIN_VALUE);
        Assert.assertEquals(b.previousValue(0), Integer.MIN_VALUE);
    }

    @Test(groups = "1s")
    public void testPreviousValue3() throws ContradictionException {
        x.removeValue(3, Cause.Null);
        Assert.assertEquals(b.previousValue(10), 0);
        Assert.assertEquals(b.previousValue(2), 0);
        Assert.assertEquals(b.previousValue(1), 0);
        Assert.assertEquals(b.previousValue(0), Integer.MIN_VALUE);
    }

    @Test(groups = "1s")
    public void testPreviousValueOut0() {
        Assert.assertEquals(b.previousValueOut(10), 9);
        Assert.assertEquals(b.previousValueOut(2), -1);
        Assert.assertEquals(b.previousValueOut(1), -1);
        Assert.assertEquals(b.previousValueOut(0), -1);
    }

    @Test(groups = "1s")
    public void testPreviousValueOut1() throws ContradictionException {
        x.instantiateTo(3, Cause.Null);
        Assert.assertEquals(b.previousValueOut(10), 9);
        Assert.assertEquals(b.previousValueOut(2), 0);
        Assert.assertEquals(b.previousValueOut(1), 0);
        Assert.assertEquals(b.previousValueOut(0), -1);
    }

    @Test(groups = "1s")
    public void testPreviousValueOut2() throws ContradictionException {
        x.removeValue(3, Cause.Null);
        Assert.assertEquals(b.previousValueOut(10), 9);
        Assert.assertEquals(b.previousValueOut(2), 1);
        Assert.assertEquals(b.previousValueOut(1), -1);
        Assert.assertEquals(b.previousValueOut(0), -1);
    }

    @Test(groups="1s", timeOut=60000)
    public void testGetValueIterator() {
        DisposableValueIterator vit = b.getValueIterator(true);
        Assert.assertTrue(vit.hasNext());
        Assert.assertEquals(0, vit.next());
        Assert.assertTrue(vit.hasNext());
        Assert.assertEquals(1, vit.next());
        Assert.assertFalse(vit.hasNext());
        vit.dispose();

        vit = b.getValueIterator(false);
        Assert.assertTrue(vit.hasPrevious());
        Assert.assertEquals(1, vit.previous());
        Assert.assertTrue(vit.hasPrevious());
        Assert.assertEquals(0, vit.previous());
        Assert.assertFalse(vit.hasPrevious());
        vit.dispose();
    }

    @Test(groups="1s", timeOut=60000)
    public void testGetRangeIterator() {
        DisposableRangeIterator rit = b.getRangeIterator(true);
        Assert.assertTrue(rit.hasNext());
        Assert.assertEquals(0, rit.min());
        Assert.assertEquals(1, rit.max());
        rit.next();
        Assert.assertFalse(rit.hasNext());

        rit = b.getRangeIterator(false);
        Assert.assertTrue(rit.hasPrevious());
        Assert.assertEquals(0, rit.min());
        Assert.assertEquals(1, rit.max());
        rit.previous();
        Assert.assertFalse(rit.hasPrevious());
    }

    @Test(groups="1s", timeOut=60000)
    public void test1(){
        BoolVar[] doms = new BoolVar[6];
        for(int i = 0 ; i < 6; i++){
            doms[i] = model.intEqView(x, i);
        }
        while(model.getSolver().solve()){
            System.out.printf("%s\n", x);
            System.out.printf("%s\n", Arrays.toString(doms));

        }
    }
}