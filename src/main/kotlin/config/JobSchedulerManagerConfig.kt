package com.test.config

import org.quartz.Scheduler
import org.quartz.SchedulerFactory
import org.quartz.impl.StdSchedulerFactory
import java.util.Properties

class JobSchedulerManagerConfig {
    var scheduler: Scheduler

    init {
        val props = Properties()
        props["org.quartz.scheduler.instanceName"] = "ChuckNorrisScheduler"
        props["org.quartz.threadPool.threadCount"] = "3"

        props["org.quartz.dataSource.postgresql.driver"] = "org.postgresql.Driver"
        props["org.quartz.dataSource.postgresql.URL"] = "jdbc:postgresql://localhost:5432/mydb"
        props["org.quartz.dataSource.postgresql.user"] = "myuser"
        props["org.quartz.dataSource.postgresql.password"] = "mypassword"
        props["org.quartz.dataSource.postgresql.maxConnections"] = "10"

        props["org.quartz.jobStore.class"] = "org.quartz.impl.jdbcjobstore.JobStoreTX"
        props["org.quartz.jobStore.driverDelegateClass"] = "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate"
        props["org.quartz.jobStore.tablePrefix"] = "QRTZ_"
        props["org.quartz.jobStore.dataSource"] = "postgresql"

        props["org.quartz.plugin.triggHistory.class"] = "org.quartz.plugins.history.LoggingTriggerHistoryPlugin"
        props["org.quartz.plugin.triggHistory.triggerFiredMessage"] = """Trigger {1}.{0} fired job {6}.{5} at: {4, date, HH:mm:ss MM/dd/yyyy}"""
        props["org.quartz.plugin.triggHistory.triggerCompleteMessage"] = """Trigger {1}.{0} completed firing job {6}.{5} at {4, date, HH:mm:ss MM/dd/yyyy}"""

        val schedulerFactory: SchedulerFactory = StdSchedulerFactory(props)
        scheduler = schedulerFactory.scheduler
    }

    fun startScheduler() {
        scheduler.start()
    }
}