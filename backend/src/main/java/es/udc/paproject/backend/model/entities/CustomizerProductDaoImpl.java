package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomizerProductDaoImpl implements CustomizerProductDao{

    @PersistenceContext
    private EntityManager entityManager;

    private String[] getTokens(String keywords) {

        if (keywords == null || keywords.length() == 0) {
            return new String[0];
        } else {
            return keywords.split("\\s");
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public Slice<Product> find(Long categoryId, String text, int page, int size) {
        String[] tokens = getTokens(text);
        String queryString = "SELECT p FROM Product p WHERE ";
        queryString+= " p.finishingDate > CURRENT_TIMESTAMP ";

        if (categoryId != null || tokens.length > 0){
            queryString += " AND ";
        }

        if(categoryId != null){
            queryString += "p.category.id = :categoryId";
        }

        if(tokens.length > 0){
            if(categoryId != null){
                queryString += " AND ";
            }
            for(int i = 0; i<tokens.length-1; i++){
                queryString += "LOWER(p.name) LIKE LOWER(:token" + i + ") AND";
            }

            queryString += "LOWER(p.name) LIKE LOWER(:token" + (tokens.length-1) + ")";
        }

        queryString += " ORDER BY p.name";

        Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);

        if(categoryId!=null){
            query.setParameter("categoryId",categoryId);
        }

        if(tokens.length != 0){
            for(int i = 0; i< tokens.length; i++){
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }
        }

        List<Product> productList = query.getResultList();
        boolean hasNext = productList.size() == (size+1);

        if(hasNext){
            productList.remove(productList.size()-1);
        }

        return new SliceImpl<>(productList, PageRequest.of(page, size),hasNext);
    }

}
