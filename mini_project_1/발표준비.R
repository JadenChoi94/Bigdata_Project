# 패키지 준비
library(dplyr)
library(stringr)
library(ggplot2)
library(tree)
library(rpart)
library(randomForest)
library(nnet)
library(neuralnet)
library(corrplot)

data_raw <- read.csv('Data/sales.csv')
vitamin <- data_raw %>%
  filter(CATEGORY == '비타민음료')
vitamin <- vitamin[,-c(1,2)]

idx<-sample(1:nrow(vitamin), size=nrow(vitamin)*0.7, replace=F)
vitamin_train<-vitamin[idx,]
vitamin_test<-vitamin[-idx,]
dim(vitamin_train); dim(vitamin_test)
# ITEM_CNT	상품품목수
# QTY	판매량
# PRICE	가격
# MAXTEMP	기온
# SALEDAY	영업(판매)일수
# RAIN_DAY	강우일수
# HOLIDAY	휴일일수

colSums(is.na(vitamin))
vitamin <- vitamin[complete.cases(vitamin),]

cor(vitamin)
corrplot(cor(vitamin))

fit1 <- lm(QTY~., data = vitamin)
summary(fit1)

par(mfrow = c(2,2))
plot(fit1)

# 다중회귀분석 69p
lm_fit<-lm(QTY~., data=vitamin_train)
summary(lm_fit)

lm_fit2=step(lm_fit, method='both')
summary(lm_fit2)

lm_pdt=predict(lm_fit2, newdata = vitamin_test)
lm_pdt

mean((lm_pdt-vitamin_test$QTY)^2)

par(mfrow=c(2,2))
plot(lm_fit2)
shapiro.test(vitamin$QTY)

foo=mean((lm_pdt-vitamin_train$QTY)^2)
sqrt(foo)

# Decision Tree, 72p
tree_fit<-tree(QTY~., data=vitamin_train)
summary(tree_fit)
plot(tree_fit)
text(tree_fit, pretty=0)
tree_yhat<-predict(tree_fit, newdata=vitamin_test)
sqrt(mean((tree_yhat-vitamin_test$QTY)^2))
# 평균제곱오차(MSE)는 약 44401.7이며, 그 제곱근은 약 210.7171 이었습니다. 즉 의사결정트리를 이용한 모델의 예측값은
# 실제 판매량의 약 210개 이내에 있다는 것을 의미합니다.
rpart_fit<-rpart(QTY~., data=vitamin_test)
summary(rpart_fit)

# 인공 신경망 기법
#1.정규화 함수 작성
normalize<-function(x){
  return((x-min(x))/(max(x)-min(x)))
}
vitamin_train_norm=as.data.frame(sapply(vitamin_train, normalize))
vitamin_test_norm=as.data.frame(sapply(vitamin_test, normalize))

#nnet 이용한 인공신경망 분석
nnet_fit=nnet(QTY~.,data=vitamin_train_norm,size=5) #인공 신경망 적합하기
nnet_pdt=predict(nnet_fit, vitamin_test_norm, type='raw')#예측결과생성
hoo_nnet=mean((nnet_pdt-vitamin_test_norm$QTY)^2)#평균제곱오차 계산
hoo_nnet
sqrt(hoo_nnet)

#neuralnet 인공신경망 분석
neural_fit=neuralnet(QTY~., data=vitamin_train_norm, hidden=2)
neural_pdt=compute(neural_fit, vitamin_test_norm)
neural_pdt
boo_neur=mean((neural_pdt$net.result-vitamin_test_norm$QTY)^2)
boo_neur
sqrt(boo_neur)

plot(neural_fit)


#random forest
set.seed(123)
rf_fit=randomForest(QTY~., data=vitamin_train, mtry=6, importance=T)
plot(rf_fit)
importance(rf_fit)
varImpPlot(rf_fit)

rf_pdt=predict(rf_fit, newdata = vitamin_test)
foo_rf=mean((rf_pdt-vitamin_test$QTY)^2)
sqrt(foo_rf)

