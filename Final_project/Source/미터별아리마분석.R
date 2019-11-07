library(tseries)
library(TTR)
library(forecast)
library(ggplot2)

data <- read.csv(file = "D:/Bigdata_Project/Final_project/data/Dataset/arima.csv", header = T)
data2<-data[,-1]# date 제거
data3<-data2[,1]# 한열씩 가져오자
timeSeriesObj = ts(data3,start=c(2014,1,1),frequency=365.25)

auto.arima(diff(log(timeSeriesObj)))
#===============================================================

tsdiag(auto.arima(diff(log(timeSeriesObj))))

fit <- arima(log(timeSeriesObj), c(4, 0, 0), 
             seasonal = list(order = c(0, 1, 1),period = 12))

pred <- predict(fit, n.ahead = 1*12)# 1년 예측

ts.plot(timeSeriesObj,2.718^pred$pred, log = "y", lty = c(1,3))

#===============================================================
# https://otexts.com/fppkr/arima-r.html
(fit <- Arima(timeSeriesObj, order=c(4,0,0)))
checkresiduals(fit)
autoplot(forecast(fit))
autoplot(fit)

#===============================================================
#수민씨꺼
auto.arima(ts(data3))
(fit = Arima(data3, order = c(1,0,1)))
checkresiduals(fit)
autoplot(forecast(fit))
forecast(fit)

forecast.Arima(arima(ts(data3), order = c(1,0,1)), h = 24)# 안됨

