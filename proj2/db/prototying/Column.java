package db.prototying;

import db.prototying.ClassChecker.*;
import db.prototying.Operator.Compartor;
import db.prototying.Operator.Operator;

/**
 * Created by zitongyang on 2/24/17.
 */
public class Column<Item> {
    private Map<Integer, Item> content;

    public static Column columnOperation(Column operand0, Column operand1, String operator) {
        try {
            Operator op = new Operator(operator);
            Column result = new Column();
            int index = 1;
            for (int i = 0; i < operand0.size(); i++) {
                if (operand0.get(i).toString().equals("NaN") && (!operator.equals("/"))) {
                    result.addByIndex(index, operand0.get(0));
                } else if (operand1.get(i).toString().equals("NaN") && (!operator.equals("/"))) {
                    result.addByIndex(index, operand1.get(0));
                } else {
                    try {
                        result.addByIndex(index, op.operate((Integer) operand0.get(index), (Integer) operand1.get(index)));
                    } catch (ClassCastException e1) {
                        try {
                            result.addByIndex(index, op.operate((Float) operand0.get(index), (Float) operand1.get(index)));
                        } catch (ClassCastException e2) {
                            try {
                                result.addByIndex(index, op.operate((String) operand0.get(index), (String) operand1.get(index)));
                            } catch (ClassCastException e3) {
                                try {
                                    result.addByIndex(index, op.operate((Float) operand0.get(index), (Integer) operand1.get(index)));
                                } catch (ClassCastException e4) {
                                    try {
                                        result.addByIndex(index, op.operate((Integer) operand0.get(index), (Float) operand1.get(index)));
                                    } catch (ClassCastException e5) {
                                        System.out.println("column oprate exception need");

                                    }

                                }

                            }

                        }
                    }
                }
                    index = index + 1;

                }
                return result;

        } catch (Exception e) {
            System.out.println("exception needed");
            return null;
        }

    }


    public Column() {
        content = new DequeMap<>();
    }

    public int size() {
        return content.size();
    }

    public void addNext(Item elem) {
        int prevSize = size();
        content.put(prevSize + 1, elem);
    }

    public void addByIndex(int index, Item elem) {
        content.put(index, elem);
    }

    public String printEntry(int index) {
        if (ClassChecker.isInteger(content.get(index).toString())) {
            return content.get(index).toString();
        } else {
            try {
                return String.format("%.3f", Float.parseFloat((String) content.get(index)));
            } catch (Exception e) {
                return content.get(index).toString();
            }

        }
    }

    public Item get(int index) {
        return content.get(index);
    }

    public void removeEntry(int index) {
        content.remove(index);
        for (int i = index+1; i<content.size()+1; i++){
            indexMinusOne(i);
        }
    }

    private void indexMinusOne(int index){
        content.keyMinusOne(index);
    }

    public static List<Integer> columnCondition(Column operand0, Column operand1, String comparotor) {
        List<Integer> result = new LinkedListDeque<>();
        try {
            Compartor com = new Compartor(comparotor);
            int index = 1;

            for (int i = 0; i < operand0.size(); i++) {
                boolean cmp;
                try {
                    cmp = com.compare((Integer) operand0.get(index), (Integer) operand1.get(index));
                } catch (ClassCastException e1) {
                    try {
                        cmp = com.compare((Float) operand0.get(index), (Float) operand1.get(index));
                    } catch (ClassCastException e2) {
                        try {
                            cmp = com.compare((String) operand0.get(index), (String) operand1.get(index));
                        } catch (ClassCastException e3) {
                            try {
                                cmp = com.compare((Float) operand0.get(index), (Integer) operand1.get(index));
                            } catch (ClassCastException e4) {
                                try {
                                    cmp = com.compare((Integer) operand0.get(index), (Float) operand1.get(index));
                                } catch (ClassCastException e5) {
                                    System.out.println("column oprate exception need");
                                    cmp = false;
                                }

                            }

                        }

                    }

                }
                if (!cmp) {
                    result.addLast(index);
                }
                index = index + 1;
            }
            return result;
        } catch (Exception e) {
            System.out.println("exception needed");
            return null;
        }

    }
}
