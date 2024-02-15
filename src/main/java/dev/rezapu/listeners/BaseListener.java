package dev.rezapu.listeners;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

abstract class BaseListener extends ListenerAdapter {



//    protected <T extends BaseListener> T createProtectedRoute(){
//
//    }
//    protected <T extends BaseDAO<?>> T createProtectedDAO(MessageReceivedEvent event, Class<T> clazz) throws InstantiationException, Exception{
//        MemberDAO memberDAO = new MemberDAO();
//        if(memberDAO.getByDiscordId(event.getMessage().getAuthor().getId()) == null){
//            throw new Exception("User not registered");
//        }
//        if(clazz.isAssignableFrom(MemberDAO.class)){
//            return clazz.cast(memberDAO);
//        }
//        try{
//            return clazz.getDeclaredConstructor().newInstance();
//        }
//        catch (Exception e){
//            throw new InstantiationException("Failed instantiating DAO");
//        }
//    }
//
//    protected <T extends BaseDAO<?>> T createPublicDAO(Class<T> clazz) throws InstantiationException{
//        try{
//            return clazz.getDeclaredConstructor().newInstance();
//        }
//        catch (Exception e){
//            throw new InstantiationException();
//        }
//    }
}
