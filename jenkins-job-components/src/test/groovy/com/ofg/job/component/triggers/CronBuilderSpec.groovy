package com.ofg.job.component.triggers

import spock.lang.Specification
import spock.lang.Unroll

class CronBuilderSpec extends Specification {
    private CronBuilder builder = new CronBuilder()

    def 'should generate proper cron for minutes'() {
        given:
            builder.every(5).minutes()

        when:
            String cron = builder.build();

        then:
            cron == 'H/5 * * * *'
    }

    def 'should generate proper cron for hours'() {
        given:
            builder.every(5).hours()

        when:
            String cron = builder.build();

        then:
            cron == 'H H/5 * * *'
    }

    def 'should generate proper cron when midnight expression used'() {
        given:
            builder
                    .withCronExpression()
                    .midnight()

        when:
            String cron = builder.build()

        then:
            cron == '@midnight'
    }

    def 'should generate proper cron when every hour expression used'() {
        given:
            builder
                    .withCronExpression()
                    .atEveryHour()

        when:
            String cron = builder.build()

        then:
            cron == '@hourly'
    }

    @Unroll
    def 'should generate proper cron when at specific hour: #hour expression used'() {
        given:
            builder
                    .withCronExpression()
                    .atHour(hour)

        when:
            String cron = builder.build()

        then:
            cron == "H $hour * * *".toString()

        where:
            hour << [6, 12, 18]
    }

    def 'should throw exception when nothing was invoked'() {
        given:
            CronBuilder builder = new CronBuilder()

        when:
            builder.build()

        then:
            thrown IllegalStateException
    }

    @Unroll
    def 'should throw exception when #value value passed'() {
        when:
            builder.every(value).hours()

        then:
            thrown IllegalArgumentException

        where:
            value << [-1, 0]
    }
}
