package com.example.hrm.exception.dayoff;

import com.example.hrm.exception.ExceptionCode;

public enum DayOffExceptionCode implements ExceptionCode {

    DATE_FROM_IS_AFTER_DATE_TO,
    DATE_IS_PAST,
    ALREADY_TAKEN,
    EXCESSIVE_DURATION,
}
