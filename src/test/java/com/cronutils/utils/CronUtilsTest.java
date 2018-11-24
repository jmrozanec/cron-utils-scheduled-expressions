package com.cronutils.utils;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.definition.TestCronDefinitionsFactory;
import com.cronutils.model.field.CronFieldName;
import com.cronutils.parser.CronParser;
import org.junit.Test;

import javax.ejb.ScheduleExpression;

import static org.junit.Assert.*;

public class CronUtilsTest {

    @Test
    public void testAsScheduleExpression(){
        final CronDefinition cronDefinition = TestCronDefinitionsFactory.quartzNoDoWAndDoMRestrictionBothSameTime();
        final CronParser cronParser = new CronParser(cronDefinition);
        final Cron cron = cronParser.parse("0 * * 1 * MON *");
        ScheduleExpression expression = CronUtils.asScheduleExpression(cron);

        assertNotNull(expression);
        assertEquals(cron.retrieve(CronFieldName.SECOND).getExpression().asString(), expression.getSecond());
        assertEquals(cron.retrieve(CronFieldName.MINUTE).getExpression().asString(), expression.getMinute());
        assertEquals(cron.retrieve(CronFieldName.HOUR).getExpression().asString(), expression.getHour());
        assertEquals(cron.retrieve(CronFieldName.DAY_OF_MONTH).getExpression().asString(), expression.getDayOfMonth());
        assertEquals(cron.retrieve(CronFieldName.MONTH).getExpression().asString(), expression.getMonth());
        assertEquals(cron.retrieve(CronFieldName.DAY_OF_WEEK).getExpression().asString(), expression.getDayOfWeek());
        assertEquals(cron.retrieve(CronFieldName.YEAR).getExpression().asString(), expression.getYear());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAsScheduleExpressionQuestionMarkFails() {
        final CronDefinition quartzcd = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
        final CronParser quartz = new CronParser(quartzcd);
        final Cron cron = quartz.parse("0 * * ? * MON *");
        CronUtils.asScheduleExpression(cron);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAsScheduleExpressionDoYNotSupported() {
        final CronDefinition cronDefinition = TestCronDefinitionsFactory.withDayOfYearDefinitionWhereNoQuestionMarkSupported();
        final CronParser cronParser= new CronParser(cronDefinition);
        final Cron cron = cronParser.parse("0 0 0 1 1-3 * * 1/14");
        CronUtils.asScheduleExpression(cron);
    }

}