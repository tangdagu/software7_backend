import numpy as np
import pandas as pd
import pymysql
import argparse
from lifelines import CoxPHFitter
import statsmodels.api as sm
import patsy
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sqlalchemy import create_engine
import json


# CoxPHFitter.fit()方法不支持直接使用默认参数来指定时间列和事件发生列的列名，采用自定义函数的方法
def fit_coxph_with_defaults(cph, table, duration_col=None, event_col=None):
    if duration_col is None:
        duration_col = table.columns[-2]  # 倒数第二列的列名
    if event_col is None:
        event_col = table.columns[-1]  # 倒数第一列的列名
    cph.fit(table, duration_col=duration_col, event_col=event_col)


def logistic(table_name, feature_names, conn):

    # 根据表名从数据库中读取数据
    query = f"SELECT * FROM {table_name}"
    data = pd.read_sql_query(query, conn)
    # data = pd.read_csv('C:\\Users\\hp-pc\\Desktop\\test_diabete.csv')

    # 筛选出包含特征列和标签列的数据
    selected_data = data[feature_names + ['label']].copy()

    # 删除包含缺失值的行
    selected_data.dropna(inplace=True)

    # 添加一个常数列作为截距
    selected_data['intercept'] = 1

    # 将时间列和列表中的每一列进行交互项组合
    for column in feature_names:
        selected_data[f'time_{column}_interaction'] = data['time'] * selected_data[column].copy()

    # 将特征和标签分开
    X = selected_data.drop(['label'], axis=1)
    y = selected_data['label']

    # 初始化 Logit 模型
    model = sm.Logit(y, X)

    # 拟合模型
    result = model.fit(disp=0)

    return result


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    # parser.add_argument("--disease", type=str, default="糖尿病")
    parser.add_argument("--factor", type=str, default="drink,smoking")
    parser.add_argument("--index", type=str, default="mean")
    parser.add_argument("--table-name", type=str, default="diabete")
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
    # conn = pymysql.connect(
    #     host=url,
    #     user=user,
    #     password=password,
    #     database=database
    # )


    engine = create_engine('mysql+pymysql://root:root@localhost:3306/software7?charset=utf8mb4')

    # 调用函数
    result = logistic(table, factor, engine)
    a = [[1,2],[2,3]]
    # 初始化一个空列表用于存储特征指标的 JSON 数据
    feature_indicators = []

    # 遍历模型中的特征名称和相应的指标
    for feature, coef, p_value, z_value, std_err in zip(result.params.index,
                                                        result.params,
                                                        result.pvalues,
                                                        result.tvalues,
                                                        result.bse):
        # 将科学计数法表示的数字转换为常规数字形式
        coef_str = '{:.10f}'.format(coef)
        p_value_str = '{:.10f}'.format(p_value)
        z_value_str = '{:.10f}'.format(z_value)
        std_err_str = '{:.10f}'.format(std_err)

        # 创建一个包含特征指标的 JSON 对象
        feature_indicator = {
            'riskFactor': feature,
            'coef': "{:.5f}".format(coef),
            'pValue': "{:.5f}".format(p_value),
            'z': "{:.5f}".format(z_value),
            'stdErr': "{:.5f}".format(std_err),
            'low': "{:.5f}".format(coef - 1.96 * std_err),  # 95% 置信区间的下界
            'top': "{:.5f}".format(coef + 1.96 * std_err)  # 95% 置信区间的上界
        }

        # 将特征指标添加到列表中
        feature_indicators.append(feature_indicator)

    # 将特征指标列表转换为 JSON 格式的字符串
    json_data = json.dumps(feature_indicators, indent=4)

    # 打印 JSON 格式的数据
    print(json_data)
