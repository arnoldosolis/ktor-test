package com.test

import com.test.config.JobSchedulerManagerConfig
import com.test.jobs.MyJob
import io.ktor.server.application.*
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder

fun main(args: Array<String>) {
    val jobScheduler = JobSchedulerManagerConfig().scheduler
    val jobId = "print-hello-every-minute"
    val job: JobDetail = JobBuilder.newJob(MyJob::class.java)
        .withIdentity(jobId, MyJob.WATCH_JOB_GROUP)
        .usingJobData(MyJob.JOB_MAP_NAME_ID_KEY, "value")
        .build();

    val triggerId  = "print-hello-every-minute-trigger"
    val trigger: Trigger = TriggerBuilder.newTrigger()
        .withIdentity(triggerId, MyJob.WATCH_JOB_GROUP)
        .withSchedule(
            SimpleScheduleBuilder.simpleSchedule()
                //every minute
                .withIntervalInMinutes(1)
                .repeatForever()
        )
        .build()

    jobScheduler.scheduleJob(job, trigger)

    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jobSchedulerManager = JobSchedulerManagerConfig()
    jobSchedulerManager.startScheduler()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
