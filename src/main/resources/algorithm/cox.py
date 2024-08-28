import numpy as np
import pandas as pd
import pymysql
import argparse
from lifelines import CoxPHFitter


# CoxPHFitter.fit()方法不支持直接使用默认参数来指定时间列和事件发生列的列名，采用自定义函数的方法
def fit_coxph_with_defaults(cph, table, duration_col=None, event_col=None):
    if duration_col is None:
        duration_col = table.columns[-2]  # 倒数第二列的列名
    if event_col is None:
        event_col = table.columns[-1]  # 倒数第一列的列名
    cph.fit(table, duration_col=duration_col, event_col=event_col)


def cox_proportional_hazard_model(table_name, feature_names, conn):


    # 根据表名从数据库中读取数据
    query = f"SELECT * FROM {table_name}"
    data = pd.read_sql_query(query, conn)

    # 将数据表根据 ID 列分为三个时间段的表
    table_1 = data[data['ID'].str.endswith('_1')]
    table_2 = data[data['ID'].str.endswith('_2')]
    table_3 = data[data['ID'].str.endswith('_3')]

    # 创建 CoxPHFitter 对象
    cph = CoxPHFitter()

    # 在每个时间段的表上分别拟合 Cox 比例风险模型，并计算风险比
    risk_ratios = []
    for table in [table_1, table_2, table_3]:
        fit_coxph_with_defaults(cph, table)
        rr = cph.hazard_ratios_
        # 根据输入的特征名过滤风险比结果
        filtered_rr = rr[feature_names]
        risk_ratios.append(filtered_rr)

    # 将风险比结果转换为二维数组
    risk_ratios_array = np.around(risk_ratios,decimals=3)

    return risk_ratios_array.tolist()



if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    # parser.add_argument("--disease", type=str, default="糖尿病")
    parser.add_argument("--factor", type=str, default="BMI")
    parser.add_argument("--index", type=str, default="mean")
    parser.add_argument("--table-name", type=str, default="diabetes")
    # parser.add_argument('--database-url', type=str, default='10.16.48.219:3306/software7')
    parser.add_argument('--database-url', type=str, default='localhost:3306/software7')
    # parser.add_argument('--database-password', type=str, default='111111')
    parser.add_argument('--database-password', type=str, default='root')
    parser.add_argument('--database-user', type=str, default='root')
    args = parser.parse_args()
    # disease = args.disease.split(',')
    factor = args.factor.split(',')
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
    risk_ratios_array = cox_proportional_hazard_model(table, factor, conn)
    print(risk_ratios_array)
