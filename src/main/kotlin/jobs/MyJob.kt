package com.test.jobs

import io.ktor.util.logging.*
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.Logger
import java.time.Instant
import java.util.Date


class MyJob() : Job {
    override fun execute(p0: JobExecutionContext?) {
        val time = Date.from(Instant.now())
        println("Hello $time")
    }

    companion object {
        const val JOB_MAP_NAME_ID_KEY = "name"
        const val WATCH_JOB_GROUP = "WatchJob"
    }
}