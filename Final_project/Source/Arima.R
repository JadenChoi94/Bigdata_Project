install.packages("forecast")
install.packages("TTR")
library(TTR)
library(forecast)
library(ggplot2)

data <- read.csv(file = "D:/Bigdata_Project/Final_project/data/Dataset/arima3.csv", header = T)
data2<-data[,-1]
data2<-data[,1]
timeSeriesObj = ts(data2,start=c(2014,1,1),frequency=365.25)


#=============================================================================
timeSeriesObj %>% stl(s.window='periodic') %>% seasadj -> eeadj
autoplot(eeadj)

eeadj %>% diff %>% ggtsdisplay(main="")
(fit <- Arima(eeadj, order=c(1,0,1)))
fit
checkresiduals(fit)
autoplot(forecast(fit))
autoplot(fit)

#=============================================================================
# 데이터 분해 - trend, seasonal, random 데이터 추세 확인
birth_comp <- decompose(timeSeriesObj)
plot(birth_comp)
#=============================================================================
# ACF : 자기상관 함수 AutoCorrelation Function
# PACF : 부분 자기상관 함수 Partial AutoCorrelation Function 

acf(timeSeriesObj)

pacf(timeSeriesObj)
#=============================================================================

decompose(timeSeriesObj)
# 3) 시계열 데이터를 이동평균한 값 생성
SMA(timeSeriesObj, n = 이동평균수)

# 4) 시계열 데이터를 차분
diff(timeSeriesObj, differences = 차분횟수)

# 5) ACF 값과 그래프를 통해 래그 절단값을 확인
acf(timeSeriesObj, lag.max = 래그수)

# 6) PACF 값과 그래프를 통해 래그 절단값을 확인
pacf(timeSeriesObj, lag.max = 래그수)

# 7) 데이터를 활용하여 최적의 ARIMA 모형을 선택
auto.arima(timeSeriesObj)

# 8) 선정된 ARIMA 모형으로 데이터를 보정(fitting)
fit = arima(timeSeriesObj, order = c(1,0,1))

fit_resid = residuals(fit)

Box.test(fit_resid, lag = 10, type  = "Ljung-Box")
#forecast using the arima model1
forecast_x = forecast( fit, h = 100) # N-steps ahead forecast

# 9) ARIMA 모형에 의해 보정된 데이터를 통해 미래값을 예측
forecast.Arima(timeSeriesObj,order = c(1,0,1), h = 24)

# 10) 시계열 데이터를 그래프로 표현
plot.ts(timeSeriesObj)

# 11) 예측된 시계열 데이터를 그래프로 표현
plot.forecast(timeSeriesObj)

#=======================================================================

#=======================================================================
auto.arima(ts(data2))
arima(ts(data2), order=c(0,1,0))
forecast.Arima(arima(ts(kings), order=c(0,1,1)) , h=10 )
plot.forecast(forecast.Arima(arima(ts(kings), order=c(0,1,1)), h=10))



