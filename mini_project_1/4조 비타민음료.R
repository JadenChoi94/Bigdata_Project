setwd('D:/NCS_ML')
data=read.csv('sales.csv')
head(data)
table(data$CATEGORY)

library(dplyr)
data2<-data %>% 
  filter(data$CATEGORY=='비타민음료')
data2<-data2[0:9]
write.csv(data2, 'D:/NCS_ML/4조비타민.csv')

data4<-lm(QTY~ITEM_CNT+PRICE+MAXTEMP+SALEDAY+RAIN_DAY+HOLIDAY, data=data2)
summary(data4)

kk=sample(1:60, 300, replace=T)
data3=data2[kk,]
data3

shapiro.test(data2$QTY)
shapiro.test(sqrt(data2$QTY))

install.packages('car')
library(car)
powerTransform(data3$QTY)
foo<-data3$QTY^0.6450462
shapiro.test(foo)

install.packages('nortest')
library(nortest)
ad.test(foo)
