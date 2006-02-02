
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2006, Dawid Weiss, Stanisław Osiński.
 * Portions (C) Contributors listed in "carrot2.CONTRIBUTORS" file.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package com.dawidweiss.carrot.filter.stc.algorithm;

import java.util.*;

/**
 * A phrase is a single sentence describing a cluster. A {@link com.dawidweiss.carrot.filter.stc.algorithm.BaseCluster}
 * has one {@link Phrase} describing it. A {@link MergedCluster} may have more then
 * one {@link Phrase} as its description.
 */
public final class Phrase
{
    /** "Most specific" phrase flag. */
    public boolean mostSpecific;

    /** "Most general" phrase flag. */
    public boolean mostGeneral;

    /**
     * Fraction of documents of a {@link MergedCluster} this phrase
     * is present in.
     */
    private float coverage;

    /**
     * Each {@link MergedCluster} may have more then one description phrase.
     * Phrases marked with {@link #selected} field are the best candidates
     * to be present as the cluster label.  
     */
    private boolean selected;

    /**
     * A collection of {@link String} of this phrase
     */
    private final List phrase;

    /** Base cluster this phrase belongs to. */
    private final BaseCluster baseCluster;

    /**
     * Construction of Phrase objects allowed only within package
     * @param baseCluster Base cluster this phrase belongs to.
     */
    protected Phrase(BaseCluster baseCluster)
    {
        this.baseCluster = baseCluster;
        this.phrase = baseCluster.getNode().getPhrase();

        // Initial values of flags for this phrase.
        mostSpecific = true;
        mostGeneral = true;
        setSelected(true);
    }

    /**
     * Returns the collection of phrase terms ({@link StemmedTerm} instances).
     */
    public List getTerms()
    {
        return phrase;
    }

    /**
     * Returns the collection of phrase terms, formatted to a string.
     */
    public String userFriendlyTerms()
    {
        StringBuffer s = new StringBuffer();
        Collection terms = getTerms();

        for (Iterator i = terms.iterator(); i.hasNext();)
        {
            final StemmedTerm t = (StemmedTerm) i.next();
            final String image = t.getTerm();
            if (s.length() > 0 && 
                    !(",".equals(image) || "?".equals(image)
                            || "!".equals(image)
                            || ";".equals(image))) {
                s.append(' ');
            }
            s.append(image);
        }

        return s.toString();
    }


    /**
     * Returns the BaseCluster of this phrase
     */
    public BaseCluster getBaseCluster()
    {
        return baseCluster;
    }

    /**
     * Sets the phrase selection flag.
     * @see #selected
     */
    void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Sets the documents coverage level in a {@link MergedCluster}.
     * @see #coverage
     */
    void setCoverage(float coverage) {
        this.coverage = coverage;
    }

    /**
     * Returns the documents coverage level in a {@link MergedCluster}.
     * @see #coverage
     */
    float getCoverage() {
        return coverage;
    }

    /**
     * Sets the phrase selection flag.
     * @see #selected
     */
    boolean isSelected() {
        return selected;
    }

    public String toString()
    {
        return "[cid=" + getBaseCluster().getNode().id + ",d="
        + getBaseCluster().getNode().getSuffixedDocumentsCount() + ",c=" + getCoverage() + ",("
        + (isSelected() ? "S "
                    : "  ") + (mostSpecific ? "MS "
                                            : "   ") + (mostGeneral ? "MG"
                                                                    : "  ") + ") : "
        + getTerms() + "]";
    }
}