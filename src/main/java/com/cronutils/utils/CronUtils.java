/*
 * Copyright 2017 jmrozanec
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cronutils.utils;

import javax.ejb.ScheduleExpression;

import com.cronutils.model.Cron;
import com.cronutils.model.field.CronFieldName;

public class CronUtils {
    public static ScheduleExpression asScheduleExpression(Cron cron){
        if(cron.asString().contains("?")){
            throw new IllegalArgumentException("? not supported by ScheduleExpression");
        }
        ScheduleExpression expression = new ScheduleExpression();
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.YEAR)){
            expression.year(cron.retrieve(CronFieldName.YEAR).getExpression().asString());
        }
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.DAY_OF_YEAR)){
            throw new IllegalArgumentException("DoY not supported by ScheduleExpression");
        }
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.DAY_OF_WEEK)){
            expression.dayOfWeek(cron.retrieve(CronFieldName.DAY_OF_WEEK).getExpression().asString());
        }
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.MONTH)){
            expression.month(cron.retrieve(CronFieldName.MONTH).getExpression().asString());
        }
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.DAY_OF_MONTH)){
            expression.dayOfMonth(cron.retrieve(CronFieldName.DAY_OF_MONTH).getExpression().asString());
        }
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.HOUR)){
            expression.hour(cron.retrieve(CronFieldName.HOUR).getExpression().asString());
        }
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.MINUTE)){
            expression.minute(cron.retrieve(CronFieldName.MINUTE).getExpression().asString());
        }
        if(cron.getCronDefinition().containsFieldDefinition(CronFieldName.SECOND)){
            expression.second(cron.retrieve(CronFieldName.SECOND).getExpression().asString());
        }
        return expression;
    }
}
