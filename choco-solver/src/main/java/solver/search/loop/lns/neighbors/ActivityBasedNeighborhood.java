/*
 * Copyright (c) 1999-2012, Ecole des Mines de Nantes
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Ecole des Mines de Nantes nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package solver.search.loop.lns.neighbors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.ICause;
import solver.Solver;
import solver.exception.ContradictionException;
import solver.search.strategy.selectors.variables.ActivityBased;
import solver.variables.IntVar;

import java.util.BitSet;
import java.util.Random;

/**
 * Large Neighborhood Search based on Activity Based Search to fix variables
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 12/06/12
 */
public class ActivityBasedNeighborhood extends ANeighbor {

    public static final Logger logger = LoggerFactory.getLogger("solver");

    private final int n;
    private final IntVar[] vars;

    private final int[] bestSolution;
    private int nbFixedVars;
    private Random rd;
    private final ActivityBased abs;


    public ActivityBasedNeighborhood(Solver solver, IntVar[] vars, long seed, ActivityBased abs, int firstNbFixedVars) {
        super(solver);
        this.n = vars.length;
        this.vars = vars.clone();
        bestSolution = new int[n];
        rd = new Random(seed);
        this.abs = abs;
        this.nbFixedVars = firstNbFixedVars;
    }

    @Override
    public void recordSolution() {
        for (int i = 0; i < vars.length; i++) {
            bestSolution[i] = vars[i].getValue();
        }
    }

    @Override
    public void restrictLess() {
        nbFixedVars /= 2;
//        System.out.println("nbFixedVars " + nbFixedVars);
        if (logger.isInfoEnabled()) {
            mSolver.getMeasures().updateTimeCount();
            logger.info(">> nbFixedVars {}", nbFixedVars);
        }
    }

    @Override
    public boolean isSearchComplete() {
        return nbFixedVars == 0;
    }

    @Override
    public void fixSomeVariables(ICause cause) throws ContradictionException {
        if (!abs.sampling) {
            //TODO
            activity(cause);
            //random();
        }
    }

    private void random(ICause cause) throws ContradictionException {
        for (int k = 0; k < nbFixedVars; k++) {
            int x = rd.nextInt(vars.length + 1);
            vars[x].instantiateTo(bestSolution[x], cause);
        }
    }

    private void activity(ICause cause) throws ContradictionException {
        BitSet selected = new BitSet(vars.length);
        while (selected.cardinality() < nbFixedVars) {
            double a = Integer.MIN_VALUE;
            int idx = -1;
            for (int k = 0; k < nbFixedVars; k++) {
                double ao = abs.getActivity(vars[k]);
                if (!selected.get(k) && ao > a) {
                    a = ao;
                    idx = k;
                }
            }
            vars[idx].instantiateTo(bestSolution[idx], cause);
            selected.set(idx);
        }
    }
}
