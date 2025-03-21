/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.core.persistence;

import java.time.ZonedDateTime;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.types.State;

/**
 * This class is used to define a filter for queries to a {@link PersistenceService}.
 *
 * <p>
 * It is designed as a Java bean, for which the different properties are constraints on the query result. These
 * properties include the item name, begin and end date and the item state. A compare operator can be defined to compare
 * not only state equality, but also its decimal value ({@code <,>}).
 *
 * <p>
 * Additionally, the filter criteria supports ordering and paging of the result, so the caller can ask to only return
 * chunks of the result of a certain size (=pageSize) from a starting index (pageNumber*pageSize).
 *
 * <p>
 * All setter methods return the filter criteria instance, so that the methods can be easily chained in order to define
 * a filter.
 *
 * @author Kai Kreuzer - Initial contribution
 * @author Lyubomir Papazov - Deprecate methods using java.util and add methods that use Java8's ZonedDateTime
 * @author Mark Herwege - Copy constructor
 */
@NonNullByDefault
public class FilterCriteria {

    /** Enumeration with all possible compare options */
    public enum Operator {
        EQ("="),
        NEQ("!="),
        GT(">"),
        LT("<"),
        GTE(">="),
        LTE("<=");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        String getSymbol() {
            return symbol;
        }
    }

    /** Enumeration with all ordering options */
    public enum Ordering {
        ASCENDING,
        DESCENDING
    }

    /** filter result to only contain entries for the given item */
    private @Nullable String itemName;

    /** filter result to only contain entries that are equal to or after the given datetime */
    private @Nullable ZonedDateTime beginDate;

    /** filter result to only contain entries that are equal to or before the given datetime */
    private @Nullable ZonedDateTime endDate;

    /** return the result list from starting index pageNumber*pageSize only */
    private int pageNumber = 0;

    /** return at most this many results */
    private int pageSize = Integer.MAX_VALUE;

    /** use this operator to compare the item state */
    private Operator operator = Operator.EQ;

    /** how to sort the result list by date */
    private Ordering ordering = Ordering.DESCENDING;

    /** Filter result to only contain entries that evaluate to true with the given operator and state */
    private @Nullable State state;

    public FilterCriteria() {
    }

    public FilterCriteria(FilterCriteria filter) {
        this.itemName = filter.itemName;
        this.beginDate = filter.beginDate;
        this.endDate = filter.endDate;
        this.pageNumber = filter.pageNumber;
        this.pageSize = filter.pageSize;
        this.operator = filter.operator;
        this.ordering = filter.ordering;
        this.state = filter.state;
    }

    public @Nullable String getItemName() {
        return itemName;
    }

    public @Nullable ZonedDateTime getBeginDate() {
        return beginDate;
    }

    public @Nullable ZonedDateTime getEndDate() {
        return endDate;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Operator getOperator() {
        return operator;
    }

    public Ordering getOrdering() {
        return ordering;
    }

    public @Nullable State getState() {
        return state;
    }

    public FilterCriteria setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public FilterCriteria setBeginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public FilterCriteria setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public FilterCriteria setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public FilterCriteria setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public FilterCriteria setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public FilterCriteria setOrdering(Ordering ordering) {
        this.ordering = ordering;
        return this;
    }

    public FilterCriteria setState(State state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        return "FilterCriteria [itemName=" + itemName + ", beginDate=" + beginDate + ", endDate=" + endDate
                + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", operator=" + operator + ", ordering="
                + ordering + ", state=" + state + "]";
    }
}
