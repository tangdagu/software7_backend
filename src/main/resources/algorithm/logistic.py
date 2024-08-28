import numpy as np
import pandas as pd
import pymysql
import argparse

from sklearn.metrics import accuracy_score
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split


def logistic_regression(input_table, feature_columns, conn, stats_array, target_column=None):
    # 从MySQL中读取数据表
    query = f"SELECT * FROM {input_table}"
    df = pd.read_sql(query, conn)

    # 根据每三行进行分组，并计算指标的最大值、最小值、平均值和方差
    df_stats = df.groupby(np.arange(len(df)) // 3)[feature_columns].agg(stats_array)
    df_stats.columns = [f"{col}_{stat}" for col, stat in df_stats.columns]
    df_stats = df_stats.reset_index()
    df_stats = df_stats.reset_index(drop=True).rename(columns={'index': 'ID'})
    df_stats['ID'] = df_stats['ID'] + 1
    df_stats['ID'] = df_stats['ID'].apply(lambda x: f'{x}_1')

    # 提取ID为_1的所有行
    df_1 = df[df['ID'].str.endswith('_1')]

    # 获取目标列名（表格的最后一列）
    target_column = df.columns[-1]

    # 提取df_1的ID列和最后一列
    new_table = df_1.loc[:, ['ID', df_1.columns[-1]]]

    # 将df_stats与df_1合并为新表
    new_df = pd.merge(df_1.iloc[:, :-2], df_stats, on='ID')
    new_df = pd.merge(new_df, new_table, on='ID')

    # 进行Logistic回归
    X_new = new_df[feature_columns].values
    y_new = new_df[target_column].values
    model = LogisticRegression()
    model.fit(X_new, y_new)

    # 获取预测的相对危险度
    predicted_proba = model.predict_proba(X_new)
    relative_risk = predicted_proba[:, 1]

    # 建立一个空二维数组准备存储输出
    output_array = []

    # 添加df_1中除了第一列和最后两列以外的所有列的相对危险度，并设置不存在的列为-1
    row_1 = []
    for col in df_1.columns[1:-2]:
        feature_relative_risk1 = round(relative_risk[df_1.columns.get_loc(col)], 3)
        row_1.append(feature_relative_risk1)
    for col in new_df.columns:
        if col not in df_1.columns and col != 'ID' and col not in df_1.columns[-2:]:
            row_1.append(-1)
    output_array.append(row_1)

    # 添加new_table中除了第一列和最后两列以外的所有列的相对危险度
    row_2 = []
    for col in new_df:
        if col != 'ID' and col not in df_1.columns[-1:]:
            feature_relative_risk2 = round(relative_risk[new_df.columns.get_loc(col)], 3)
            row_2.append(feature_relative_risk2)
    output_array.append(row_2)

    return output_array


def logistic_accuracy(input_table, feature_columns, conn, stats_array, target_column=None):
    # 从MySQL中读取数据表
    query = f"SELECT * FROM {input_table}"
    df = pd.read_sql(query, conn)

    # 根据每三行进行分组，并计算指标的最大值、最小值、平均值和方差
    df_stats = df.groupby(np.arange(len(df)) // 3)[feature_columns].agg(stats_array)
    df_stats.columns = [f"{col}_{stat}" for col, stat in df_stats.columns]
    df_stats = df_stats.reset_index()
    df_stats = df_stats.reset_index(drop=True).rename(columns={'index': 'ID'})
    df_stats['ID'] = df_stats['ID'] + 1
    df_stats['ID'] = df_stats['ID'].apply(lambda x: f'{x}_1')

    # 提取ID为_1的所有行
    df_1 = df[df['ID'].str.endswith('_1')]

    # 获取目标列名（表格的最后一列）
    target_column = df.columns[-1]

    # 提取df_1的ID列和最后一列
    new_table = df_1.loc[:, ['ID', df_1.columns[-1]]]

    # 将df_stats与df_1合并为新表
    new_df = pd.merge(df_1.iloc[:, :-2], df_stats, on='ID')
    new_df = pd.merge(new_df, new_table, on='ID')

    # 进行新表Logistic回归的输入输出准备
    X_new = new_df[feature_columns].values
    y_new = new_df[target_column].values

    # 进行df_1的Logistic回归输入输出准备
    X_df = df_1[feature_columns].values
    y_df = df_1[target_column].values

    # 假设X是特征变量，y是目标变量
    X_train, X_test, y_train, y_test = train_test_split(X_new, y_new, test_size=0.3)  # 划分训练集和测试集

    # 假设X是特征变量，y是目标变量
    X_train1, X_test1, y_train1, y_test1 = train_test_split(X_df, y_df, test_size=0.3)  # 划分训练集和测试集

    # 创建逻辑回归模型并进行训练
    model = LogisticRegression()
    model.fit(X_train, y_train)
    model.fit(X_train1, y_train1)

    # 在测试集上进行预测
    y_pred = model.predict(X_test)
    y_pred1 = model.predict(X_test1)

    # 计算预测准确率
    accuracy = round(accuracy_score(y_test, y_pred), 3)
    accuracy1 = round(accuracy_score(y_test1, y_pred1), 3)

    # 存储准确率的列表
    accuracies = [accuracy1, accuracy]

    # 调用相对危险度函数
    output_array = logistic_regression(table, factor, conn, index)

    # 将第一个数据插入第一行的最后一个位置
    output_array[0].append(accuracies[0])

    # 将第一个数据插入第一行的最后一个位置
    output_array[1].append(accuracies[1])

    return output_array


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    # parser.add_argument("--disease", type=str, default="糖尿病")
    parser.add_argument("--factor", type=str, default="BMI")
    parser.add_argument("--index", type=str, default="mean,min")
    parser.add_argument("--table-name", type=str, default="diabetes")
    parser.add_argument('--database-url', type=str, default='10.16.48.219:3306/software7')
    parser.add_argument('--database-password', type=str, default='111111')
    parser.add_argument('--database-user', type=str, default='root')
    args = parser.parse_args()
    # disease = args.disease.split(',')
    factor = args.factor.split(',')
    index = args.index.split(',')
    table = args.table_name
    url = args.database_url.split(":")[0]
    user = args.database_user
    password = args.database_password
    database = args.database_url.split("/")[-1]
    conn = pymysql.connect(
        host=url,
        user=user,
        password=password,
        database=database
    )

    # 调用函数
    output_array = logistic_accuracy(table, factor, conn, index)
    print(output_array)
