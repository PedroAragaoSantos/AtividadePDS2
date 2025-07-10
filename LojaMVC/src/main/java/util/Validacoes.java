package util;

import java.util.regex.Pattern;

public class Validacoes {
    
    
     //metodo para validar o padrão de Telefone 
    public boolean ValidaFormatoTelefone(String telefone){
        String telefoneFormato = "^[(]?\\d{2}[)]?\\s?\\d{5}[-]?\\d{4}$";
        if(!Pattern.compile(telefoneFormato).matcher(telefone).matches()){
            AlertaUtil.mostrarErro("Formato do Telefone incorreto", "Verifique se o seu telefone está no padrão (XX) XXXXX-XXXX");
            return true;
        }
        return false;
    }
    
    //metodo para validar o padrão de email 
    public boolean ValidaFormatEmail(String Email){
        String emailFormat = "^[^\\s@]+@gmail\\.com$";
        if(!Pattern.compile(emailFormat).matcher(Email).matches()){
            AlertaUtil.mostrarErro("Formato do email incorreto", "Verifique se o seu email está no padrão xxxxxx@gmail.com");
            return true;
        }
        return false;
    }
    
}
