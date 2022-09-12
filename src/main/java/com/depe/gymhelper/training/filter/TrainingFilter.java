package com.depe.gymhelper.training.filter;

import com.depe.gymhelper.training.TrainingStatus;

import java.time.LocalDateTime;

public class TrainingFilter {
    private final TrainingStatus status;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public TrainingFilter(TrainingStatus status, LocalDateTime from, LocalDateTime to) {
        this.status = status;
        this.from = from;
        this.to = to;
    }

    public FilterType getFilterType() {
        if (isDateFilter() && isStatusFilter()) {
            return FilterType.STATUS_DATE;
        }
        if (isDateFilter()) {
            return FilterType.DATE;
        }
        if (isStatusFilter()) {
            return FilterType.STATUS;
        }
        return FilterType.EMPTY;
    }

    private boolean isDateFilter() {
        return this.from != null && this.to != null;
    }

    private boolean isStatusFilter() {
        return this.status != null;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public TrainingStatus getStatus() {
        return status;
    }
}
