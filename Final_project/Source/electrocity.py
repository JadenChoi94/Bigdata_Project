import pandas as pd
df=pd.read_csv('D:/HomeC.csv')
df.head(5)
df.columns
intro_colum=df.describe()

intro_colum.to_csv("./culums.csv")
