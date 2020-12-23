package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class Controller implements Initializable {

    @FXML private TextField textField;
    @FXML private TextArea textArea;

    //ForStack
    private Stack<String> stack = new Stack();

    //Opt.
    private int i = 0;
    private boolean ifItem = false;
    private boolean dot = false;

    //Choose Number
    @FXML
    private void chooseNum(MouseEvent event)
    {
        Object n = event.getSource();
        Button btn = (Button)n;
        String s = btn.getText();

        if (stack.empty() || ifItem)
        {
            stack.push(s);
            ifItem = false;
        }

        else {
            String pop = stack.pop();

            if (s.equals("-"))
            {
                Double dpop = Double.parseDouble(pop)-2*Double.parseDouble(pop);
                stack.push(dpop.toString());
            }

            else if (s.equals(".") && dot) {
                stack.push(pop);
            }

            else {
                if (s.equals("."))
                {
                    dot=true;
                }
                stack.push(pop+s);
            }
        }

        showList();
        textField.setText(stack.get(i));
    }

    //Choose Options
    @FXML
    private void chooseOpt(MouseEvent event)
    {
        Object n = event.getSource();
        Button btn = (Button)n;
        String s = btn.getText();

        if (stack.empty())
        {
            textField.clear();
            textField.setPromptText("Give out Number.");
        }

        else if (s.equals("Enter"))
        {
            i++;
            dot=false;
            ifItem=true;
        }

        else {
            stack.pop();
            textField.clear();
            if (!stack.empty()){
                i--;
            }
            if (s.equals("C")){
                while (!stack.empty())
                {
                    stack.pop();
                }
                i = 0;
            }
        }
        showList();
    }

    @FXML
    private void clickOperatorblock(MouseEvent event)
    {
        Object n = event.getSource();
        Button btn = (Button)n;
        String s = btn.getText();

        Double popx;
        Double popy;

        try
        {
            popx = Double.parseDouble(stack.pop());
            try
            {
                popy = Double.parseDouble(stack.pop());

            }
            catch (Exception ex){
                popy = null;
            }
        }

        //Err
        catch (Exception e)
        {
            popx=null;
            popy=null;
        }

        if (s.equals("1/x") && popx!=null)
        {
            stack.push(popy.toString());
            popx = 1/popx;
            stack.push(popx.toString());
        }

        else if (popx!=null && popy!=null)
        {
            Double erg = null;

            if (s.equals("+"))
            {
                erg = popx + popy;
                i--;
            }

            else if (s.equals("-"))
            {
                erg = popx - popy;
                i--;
            }

            else if (s.equals("*"))
            {
                erg = popx * popy;
                i--;
            }

            else if (s.equals("x<–>y"))
            {
                stack.push(popx.toString());
                erg = popy;
            }

            else if (s.equals("/"))
            {
                erg = popx / popy;
                i--;
            }

            stack.push(erg.toString());
        }

        else {
            textField.clear();
            textField.setPromptText("Error: Enter appropriate Numbers.");

            if (popx!=null)
            {
                stack.push(popx.toString());
                if (popy!=null) {
                    stack.push(popy.toString());
                }
            }
        }

        showList();
    }

    private String stacktoString()
    {
        StringBuilder sb =new StringBuilder();

        for (int i=stack.size()-1; i>-1;i--)
        {
            if (stack.size()-1==i)
            {
                sb.append(""+stack.get(i)+"\n");
            }

            else {
                sb.append(i+":"+stack.get(i)+"\n");
            }
        }
        return sb.toString();
    }

    private void showList(){
        textArea.setText(stacktoString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setEditable(false);
        textField.setEditable(false);
    }
}
