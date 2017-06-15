import playerpos.flagposframe as frm
import numpy as np
import sklearn.linear_model as linear_model
import sklearn.model_selection as model_selection
import math
#from sklearn.model_selection import cross_val_score, learning_curve


def get_in_out(pos):
    return frm.get_flags(pos), frm.get_xydir(pos)

def calc_loss(x, y, reg):
    predicted = x.dot(np.transpose(reg.coef_)) + reg.intercept_
    dist = np.sum(np.square(predicted - y), axis=1)
    qdrerr = dist.mean(axis=0)
    return math.sqrt(qdrerr)

def calc_score(x, y, reg):
    score = model_selection.cross_val_score(reg, x, y, scoring='neg_mean_squared_error')
    return score

def evaluate(x, y, reg):
    print('loss = ', calc_loss(x, y, reg))
    print('score = ', calc_score(x, y, reg))
    print('Shapes coef_/intercept_: ', reg.coef_.shape, reg.intercept_.shape)

def outputVsPrediction(x, y, reg):
    predicted = np.dot(x, np.transpose(reg.coef_)) + reg.intercept_
    a = np.concatenate((y, predicted), axis=1)
    print('y vs. predicted = ', a[:, [0, 3, 1, 4, 2, 5]])

def lin_reg(pos):
    print('')
    print('** Linear Regression')
    x, y = get_in_out(pos)
    reg = linear_model.LinearRegression()
    reg.fit(x, y)
    evaluate(x, y, reg)

def lin_reg_ridge(pos):
    print('')
    print('** Ridge Regression')
    x, y = get_in_out(pos)
    reg = linear_model.Ridge(alpha=0.5)
    reg.fit(x, y)
    evaluate(x, y, reg)

def lin_reg_lasso(pos):
    print('')
    print('** Lasso Regression')
    x, y = get_in_out(pos)
    reg = linear_model.Lasso(alpha = 0.5)
    reg.fit(x, y)
    evaluate(x, y, reg)

def lin_reg_quadratic(pos):
    print('')
    print('** Linear Regression with quadratic features')
    x, y = get_in_out(pos)
    xx = np.concatenate((x, x*x), axis = 1) # adding features to x
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes xx, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)

def lin_reg_quadratic_adjacent(pos):
    print('')
    print('** Linear Regression with quadratic and adjacent features eg.: flag_23 * flag_24')
    x, y = get_in_out(pos)
    xx = np.concatenate((x, x*x, x * np.roll(x,1,axis=1)), axis = 1)
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes xx, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)

def lin_reg_quadratic_adjacent_dist10(pos):
    print('')
    print('** Linear Regression with quadratic and adjacent and distance-10 features')
    x, y = get_in_out(pos)
    xx = np.concatenate((x, x*x, x * np.roll(x,1,axis=1), x * np.roll(x,10,axis=1)), axis = 1)
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes xx, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)

def lin_reg_quadratic_alldist10(pos):
    print('')
    print('** Linear Regression with quadratic and all distance-10 features')
    x, y = get_in_out(pos)
    xx = np.concatenate((x,
                         x*x,
                         x * np.roll(x,10,axis=1),
                         x * np.roll(x,20,axis=1),
                         x * np.roll(x,30,axis=1),
                         x * np.roll(x,40,axis=1)
                         ), axis = 1)
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes xx, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)

def lin_reg_quadratic_alldist10(pos):
    print('')
    step = 5
    print('** Linear Regression with quadratic and all ', step, '-shifted features')
    x, y = get_in_out(pos)
    xx = x
    for i in range(0, x.shape[1], step):
        xx = np.concatenate((xx, x * np.roll(x, i, axis=1)), axis=1)
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes x, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)

def lin_reg_quadratic_cubic(pos):
    print('')
    print('** Linear Regression with quadratic and cubic features')
    x, y = get_in_out(pos)
    xx = np.concatenate((x, x*x, x*x*x), axis = 1)
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes xx, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)

def lin_reg_quadratic_cubic_dist(pos):
    print('')
    step = 8
    print('** Linear Regression with quadratic and cubic features and all', step, '-shifted features')
    x, y = get_in_out(pos)
    xx = np.concatenate((x, x*x*x), axis = 1)
    for i in range(0, x.shape[1], step):
        xx = np.concatenate((xx, x * np.roll(x, i, axis=1)), axis=1)
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes xx, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)

def lin_reg_full_matrix(pos):
    print('')
    print('** Linear Regression with full matrix features (all flag_i * flag_j)')
    x, y = get_in_out(pos)
    # covariance using additional dimension and broadcasting, then flatten additional dimension
    v = x[:, :, np.newaxis] * x[:, np.newaxis, :]
    xx = np.concatenate((x, v.reshape(x.shape[0], -1)), axis=1)
    reg = linear_model.LinearRegression()
    reg.fit(xx, y)
    print('Shapes x, y, coef_: ', xx.shape, y.shape, reg.coef_.shape)
    evaluate(xx, y, reg)
    outputVsPrediction(xx, y, reg)