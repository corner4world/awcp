/* ========================================================================
 * ZUI: date.js
 * http://zui.sexy
 * ========================================================================
 * Copyright (c) 2014 cnezsoft.com; Licensed MIT
 * ======================================================================== */


(function()
{
    'use strict';

    /**
     * Ticks of a whole day
     * @type {number}
     */
    Date.ONEDAY_TICKS = 24 * 3600 * 1000;

    /**
     * Format date to a string
     *
     * @param  string   format
     * @return string
     */
    Date.prototype.format = function(format)
    {
        var date = {
            'M+': this.getMonth() + 1,
            'd+': this.getDate(),
            'h+': this.getHours(),
            'm+': this.getMinutes(),
            's+': this.getSeconds(),
            'q+': Math.floor((this.getMonth() + 3) / 3),
            'S+': this.getMilliseconds()
        };
        if (/(y+)/i.test(format))
        {
            format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
        }
        for (var k in date)
        {
            if (new RegExp('(' + k + ')').test(format))
            {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ('00' + date[k]).substr(('' + date[k]).length));
            }
        }
        return format;
    };

    // /**
    //  * Descript date with friendly way
    //  * @return {string}
    //  */
    // Date.prototype.friendlyStr = function()
    // {
    //     var date    = this,
    //         curDate = new Date(),
    //         year    = date.getFullYear(),
    //         month   = date.getMonth() + 10,
    //         day     = date.getDate(),
    //         hour    = date.getHours(),
    //         minute  = date.getMinutes(),
    //         curYear = curDate.getFullYear(),
    //         curHour = curDate.getHours(),
    //         timeStr;

    //     if(year < curYear)
    //     {
    //         timeStr = year +'年'+ month +'月'+ day +'日 '+ hour +':'+ minute;
    //     }
    //     else
    //     {
    //         var pastTime = curDate - date,
    //             pastH = pastTime/3600000;

    //         if(pastH > curHour)
    //         {
    //             timeStr = month +'月'+ day +'日 '+ hour +':'+ minute;
    //         }
    //         else if(pastH >= 1)
    //         {
    //             timeStr = '今天 ' + hour +':'+ minute +'分';
    //         }
    //         else
    //         {
    //               var pastM = curDate.getMinutes() - minute;
    //               if(pastM > 1)
    //               {
    //                   timeStr = pastM +'分钟前';
    //               }
    //               else
    //               {
    //                   timeStr = '刚刚';
    //               }
    //         }
    //     }
    //     return timeStr;
    // };

    /**
     * Add milliseconds to the date
     * @param {number} value
     */
    Date.prototype.addMilliseconds = function(value)
    {
        this.setTime(this.getTime() + value);
        return this;
    };

    /**
     * Add days to the date
     * @param {number} days
     */
    Date.prototype.addDays = function(days)
    {
        this.addMilliseconds(days * Date.ONEDAY_TICKS);
        return this;
    };

    /**
     * Clone a new date instane from the date
     * @return {Date}
     */
    Date.prototype.clone = function()
    {
        var date = new Date();
        date.setTime(this.getTime());
        return date;
    };

    /**
     * Judge the year is in a leap year
     * @param  {integer}  year
     * @return {Boolean}
     */
    Date.isLeapYear = function(year)
    {
        return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0));
    };

    /**
     * Get days number of the date
     * @param  {integer} year
     * @param  {integer} month
     * @return {integer}
     */
    Date.getDaysInMonth = function(year, month)
    {
        return [31, (Date.isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
    };

    /**
     * Judge the date is in a leap year
     * @return {Boolean}
     */
    Date.prototype.isLeapYear = function()
    {
        return Date.isLeapYear(this.getFullYear());
    };

    /**
     * Clear time part of the date
     * @return {date}
     */
    Date.prototype.clearTime = function()
    {
        this.setHours(0);
        this.setMinutes(0);
        this.setSeconds(0);
        this.setMilliseconds(0);
        return this;
    };

    /**
     * Get days of this month of the date
     * @return {integer}
     */
    Date.prototype.getDaysInMonth = function()
    {
        return Date.getDaysInMonth(this.getFullYear(), this.getMonth());
    };

    /**
     * Add months to the date
     * @param {date} value
     */
    Date.prototype.addMonths = function(value)
    {
        var n = this.getDate();
        this.setDate(1);
        this.setMonth(this.getMonth() + value);
        this.setDate(Math.min(n, this.getDaysInMonth()));
        return this;
    };

    /**
     * Get last week day of the date
     * @param  {integer} day
     * @return {date}
     */
    Date.prototype.getLastWeekday = function(day)
    {
        day = day || 1;

        var d = this.clone();
        while (d.getDay() != day)
        {
            d.addDays(-1);
        }
        d.clearTime();
        return d;
    };

    /**
     * Judge the date is same day as another date
     * @param  {date}  date
     * @return {Boolean}
     */
    Date.prototype.isSameDay = function(date)
    {
        return date.toDateString() === this.toDateString();
    };

    /**
     * Judge the date is in same week as another date
     * @param  {date}  date
     * @return {Boolean}
     */
    Date.prototype.isSameWeek = function(date)
    {
        var weekStart = this.getLastWeekday();
        var weekEnd = weekStart.clone().addDays(7);
        return date >= weekStart && date < weekEnd;
    };

    /**
     * Judge the date is in same year as another date
     * @param  {date}  date
     * @return {Boolean}
     */
    Date.prototype.isSameYear = function(date)
    {
        return this.getFullYear() === date.getFullYear();
    };
}());
