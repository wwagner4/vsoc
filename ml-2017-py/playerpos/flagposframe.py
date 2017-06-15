import pandas as pd
import scipy.stats as stats

# read position dataframe from csv file with structure: no, pos_x, pos_y, angle, flag_0, ... flag_41
def read_pos_frame(file_name='random_pos_1000.csv'):
    pos = pd.read_csv(file_name, header=None)
    # set column names
    flag_cols = ['flag_' + str(c - 4) for c in pos.columns[4:]]
    pos.columns = ['#', 'pos_x', 'pos_y', 'direction'] + flag_cols
    return pos

def describe_pos_frame(pos):
    print(pos.info())
    print("pos_x: ", stats.describe(pos.pos_x))
    print("pos_y: ", stats.describe(pos.pos_y))
    print("direction: ", stats.describe(pos.direction))
    # distribution of angles under which flag is seen (not seen are skipped)
    print("flag_0: ", stats.describe(pos.flag_0[pos.flag_0 > 0]))
    print("flag_25: ", stats.describe(pos.flag_25[pos.flag_25 > 0]))

def get_flags(pos):
    return pos.values[:, 4:]

def get_xydir(pos):
    return pos.values[:, 1:4]  # pos_x, pos_y, direction

def describe_visible_flags(pos):
    flgs = get_flags(pos)
    print("# of visible flags: ", stats.describe((flgs > 0).sum(axis=1)))

def analyze(file_name):
    pos = read_pos_frame(file_name)
    describe_pos_frame(pos)
    describe_visible_flags(pos)

'''
#print(pos.head())
#print(pos.info())
#print(pos.columns)
#print(pos['Angle'].value_counts()[:10])
#print(pos.query('Angle > 2*@pi or Angle < 0').shape)

# check flags for wrong values (eg. 16.45)
#print(pos.iloc[11245])
#print(pos.iloc[11245]['25'])
flags = pos.loc[:, 'Flag0':]    #'Flag41'
print('total # of flags =', flags.count().sum(), '( should be', flags.shape[0] * 42, ')')

wrongFlagsIx = (flags > 0) & (flags < 900)
print('total # of wrong flags =', wrongFlagsIx.sum().sum())

print('wrong flags on lines', flags.index[flags[wrongFlagsIx].any(axis=1)])
#print(wrongFlags.index[wrongFlags.count(axis=1) > 0].leng())
#print(wrongFlags.index[wrongFlags.any(axis=1)])
#print('line', flags.iloc[111])
#print('line', flags.iloc[130])
'''