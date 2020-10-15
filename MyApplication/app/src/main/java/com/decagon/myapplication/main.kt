package algorithm

import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration


fun main() {



    var h = 7
    var m = 0
    var time = ""

    var hours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    val hoursInWords = arrayListOf<String>("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve")

    if(m == 0){
        time = "${hoursInWords[(h-1)]} o' clock"
    } else if(m == 1) {
        time = "one minute past ${hoursInWords[(h-1)]}"
    } else if(m == 15) {
        time = "quarter past ${hoursInWords[(h-1)]}"
    }  else if(m in 20..29) {
        when(m){
            20->{time = "twenty minutes past ${hoursInWords[(h-1)]}"}
            21->{time = "twenty one minutes past ${hoursInWords[(h-1)]}"}
            22->{time = "twenty two minutes past ${hoursInWords[(h-1)]}"}
            23->{time = "twenty three minutes past ${hoursInWords[(h-1)]}"}
            24->{time = "twenty four minutes past ${hoursInWords[(h-1)]}"}
            25->{time = "twenty five minutes past ${hoursInWords[(h-1)]}"}
            26->{time = "twenty six minutes past ${hoursInWords[(h-1)]}"}
            27->{time = "twenty seven minutes past ${hoursInWords[(h-1)]}"}
            28->{time = "twenty eight minutes past ${hoursInWords[(h-1)]}"}
            29->{time = "twenty nine minutes past ${hoursInWords[(h-1)]}"}
        }
    } else if(m == 30) {
        time = "half past ${hoursInWords[(h-1)]}"
    } else if(m in 31..39) {
        when(m){
            39->{time = "twenty one minutes to ${hoursInWords[(h)]}"}
            38->{time = "twenty two minutes to ${hoursInWords[(h)]}"}
            37->{time = "twenty three minutes to ${hoursInWords[(h)]}"}
            36->{time = "twenty four minutes to ${hoursInWords[(h)]}"}
            35->{time = "twenty five minutes to ${hoursInWords[(h)]}"}
            34->{time = "twenty six minutes to ${hoursInWords[(h)]}"}
            33->{time = "twenty seven minutes to ${hoursInWords[(h)]}"}
            32->{time = "twenty eight minutes to ${hoursInWords[(h)]}"}
            31->{time = "twenty nine minutes to ${hoursInWords[(h)]}"}
        }
    }else if(m in 50..59) {
        when(m){
            50->time = "ten minutes to ${hoursInWords[(h)]}"
            51->{time = "nine minutes to ${hoursInWords[(h)]}"}
            52->{time = "eight minutes to ${hoursInWords[(h)]}"}
            53->{time = "five minutes to ${hoursInWords[(h)]}"}
            54->{time = "six minutes to ${hoursInWords[(h)]}"}
            55->{time = "five minutes to ${hoursInWords[(h)]}"}
            56->{time = "four minutes to ${hoursInWords[(h)]}"}
            57->{time = "three minutes to ${hoursInWords[(h)]}"}
            58->{time = "two minutes to ${hoursInWords[(h)]}"}
            59->{time = "one minutes to ${hoursInWords[(h)]}"}
        }
    } else if(m == 45) {
        time = "quarter to ${hoursInWords[(h)]}"
    }else if(m == 47) {
        time = "thirteen minutes to ${hoursInWords[(h)]}"
    }

    println(time)





}
