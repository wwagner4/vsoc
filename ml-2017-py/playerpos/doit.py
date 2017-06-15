import playerpos.flagposframe as frm
import playerpos.poslearn as lrn

for fi in ['../../machinelearning-2017/create-data/src/main/doc/random-pos-001/data/random_pos_1000.csv'
          #,'../../machinelearning-2017/create-data/src/main/doc/random-pos-001/data/random_pos_50000.csv'
           ]:
    pos = frm.read_pos_frame(fi)
    frm.describe_pos_frame(pos)
    frm.describe_visible_flags(pos)

    lrn.lin_reg(pos)
    lrn.lin_reg_ridge(pos)
    lrn.lin_reg_lasso(pos)
    lrn.lin_reg_quadratic(pos)
    lrn.lin_reg_quadratic_adjacent(pos)
    lrn.lin_reg_quadratic_adjacent_dist10(pos)
    lrn.lin_reg_quadratic_alldist10(pos)
    lrn.lin_reg_quadratic_cubic(pos)
    lrn.lin_reg_quadratic_cubic_dist(pos)
    lrn.lin_reg_full_matrix(pos)
#x,y = lrn.get_in_out(pos)
#print(y)
