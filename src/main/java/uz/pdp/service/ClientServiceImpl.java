package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.entity.User;
import uz.pdp.payload.filterPayload.*;
import uz.pdp.payload.filterPayload.enums.FilterTypeEnum;
import uz.pdp.repository.UserRepository;
import uz.pdp.utils.Columns;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;

    @Override
    public List<ClientDTO> getAllClients(ViewDTO viewDTO, int page, int size) {


        StringBuilder query;

        query = new StringBuilder("""
                    WITH temp AS (
                        SELECT u.id,
                               u.first_name,
                               u.last_name,
                               u.middle_name,
                               u.phone_number,
                               u.enabled
                           FROM users u
                    """);

/**
 admin filter sort yoki search qilayotganini tekshiramiz
 */

        if (Objects.nonNull(viewDTO)){
            if (viewDTO.getFiltering().getFilterType() != null)
                query.append(filter(viewDTO.getFiltering()));

            query.append(") \n SELECT * FROM temp ");

            if (!viewDTO.getSearching().isBlank())
                query.append(search(viewDTO.getSearching()));

            if (!viewDTO.getSorting().isEmpty())
                query.append(sort(viewDTO.getSorting()));

        }

//        query
//                .append(" LIMIT ")
//                .append(size)
//                .append(" OFFSET ")
//                .append(page * size);


        System.out.println(query);



        System.out.println(userRepository.getAllUsersByStringQuery(query.toString()));




        return new ArrayList<>();
    }

    private String filter(FilterDTO filterDTO) {

        if (filterDTO.getFilterType() == FilterTypeEnum.ACTIVE)
            return " WHERE  u.enabled ";
        else
            return " WHERE !u.enabled ";
    }

    private String search(String value) {

        StringBuilder query = new StringBuilder(" WHERE ");

        Columns[] columns = Columns.values();


        String column;

        for (int i = 0; i < columns.length; i++) {

            column = columns[i].name();

            query.append(column)
                    .append(" ILIKE '%")
                    .append(value)
                    .append("%'");

            if (i != columns.length-1)
                query.append(" OR ");
        }


        return query.toString();
    }

    private String sort(List<SortingDTO> sortingDTOs) {



        StringBuilder query = new StringBuilder(" ORDER BY ");

        SortingDTO sortingDTO;

        for (int i = 0; i < sortingDTOs.size(); i++) {

            sortingDTO = sortingDTOs.get(i);

            query.append(sortingDTO.getColumnName())
                    .append(" ")
                    .append(sortingDTO.getType());

            if (i != sortingDTOs.size()-1)
                query.append(", ");

        }

        return query.toString();
    }
}
