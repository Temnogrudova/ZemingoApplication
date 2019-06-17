package com.zemingo.ekaterinatemnogrudova.zemingo.utils;

import io.reactivex.Scheduler;

public interface IScheduler {

    Scheduler io();
    Scheduler ui();
    Scheduler computation();

}