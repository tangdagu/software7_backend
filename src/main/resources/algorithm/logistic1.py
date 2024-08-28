import numpy as np
import pandas as pd
import pymysql
import argparse

from sklearn.linear_model import LogisticRegression


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
    X = new_df[feature_columns].values
    y = new_df[target_column].values
    model = LogisticRegression()
    model.fit(X, y)

    # 获取预测的相对危险度
    predicted_proba = model.predict_proba(X)
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
    for col in new_df.columns:
        if col != 'ID' and col not in df_1.columns[-1:]:
            feature_relative_risk2 = round(relative_risk[new_df.columns.get_loc(col)], 3)
            row_2.append(feature_relative_risk2)
    output_array.append(row_2)

    return output_array


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    # parser.add_argument("--disease", type=str, default="糖尿病")
    parser.add_argument("--factor", type=str, default="BMI")
    parser.add_argument("--index", type=str, default="mean")
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
    output_array = logistic_regression(table, factor, conn, index)
    print(output_array)
