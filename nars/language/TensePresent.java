/*
 * TensePresent.java
 *
 * Copyright (C) 2008  Pei Wang
 *
 * This file is part of Open-NARS.
 *
 * Open-NARS is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Open-NARS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package nars.language;

import java.util.*;
import nars.io.*;
import nars.main.Memory;

/**
 * Present tense of a Statement.
 */
public class TensePresent extends Tense {
    
    /**
     * constructor with partial values, called by make
     * @param n The name of the term
     * @param arg The component list of the term
     */
    private TensePresent(String n, ArrayList<Term> arg) {
        super(n, arg);
    }
    
    /**
     * constructor with full values, called by clone
     * @param cs component list
     * @param open open variable list
     * @param closed closed variable list
     * @param i syntactic complexity of the compound
     * @param n The name of the term
     */
    private TensePresent(String n, ArrayList<Term> cs, ArrayList<Variable> open, ArrayList<Variable> closed, short i) {
        super(n, cs, open, closed, i);
    }
    
    /**
     * override the cloning methed in Object
     * @return A new object, to be casted into a SetExt
     */
    public Object clone() {
        return new TensePresent(name, (ArrayList<Term>) cloneList(components),
                (ArrayList<Variable>) cloneList(openVariables), (ArrayList<Variable>) cloneList(closedVariables), complexity);
    }
    
    /**
     * Try to make a new compound. Called by StringParser.
     * @return the Term generated from the arguments
     * @param argument The list of components
     */
    public static Term make(ArrayList<Term> argument) {
        if (argument.size() != 1) 
            return null;
        Term t = argument.get(0);
        if ((t instanceof TensePresent) || (t instanceof TensePast) || (t instanceof TenseFuture))
            return t;         
        String name = makeCompoundName(Symbols.PRESENT_OPERATOR, argument);
        t = Memory.nameToListedTerm(name);
        return (t != null) ? t : new TensePresent(name, argument);
    }
    
    /**
     * Try to make a compound of one component. Called by the inference rules.
     * @param t The compoment
     * @return A compound generated or a term it reduced to
     */
    public static Term make(Term t) {
        ArrayList<Term> argument = new ArrayList<Term>();
        argument.add(t);
        return make(argument);
    }

    /**
     * get the operator of the term.
     * @return the operator of the term
     */
    public String operator() {
        return Symbols.PRESENT_OPERATOR;
    }

    public CompoundTerm.TemporalOrder getTemporalOrder() {
        return CompoundTerm.TemporalOrder.WHEN;
    }
}