package com.epmweb.server.service.impl;

import com.epmweb.server.repository.ProductCategoryExtendRepository;
import com.epmweb.server.service.ProductCategoryExtendService;
import com.epmweb.server.service.dto.ProductCategoryDTO;
import com.epmweb.server.service.mapper.ProductCategoryMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductCategoryExtendServiceImpl implements ProductCategoryExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendServiceImpl.class);
    private final ProductCategoryExtendRepository productCategoryExtendRepository;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryExtendServiceImpl(ProductCategoryExtendRepository productCategoryExtendRepository, ProductCategoryMapper productCategoryMapper) {
        this.productCategoryExtendRepository = productCategoryExtendRepository;
        this.productCategoryMapper = productCategoryMapper;
    }

    @Override
    public JsonNode getAllProductCategories() {

        try {
            List<ProductCategoryDTO> rootCategoriesDTO = productCategoryExtendRepository.findAllByParentIdIsNull()
                .stream()
                .map(productCategoryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.createArrayNode();

            for (ProductCategoryDTO parentCategoryDTO : rootCategoriesDTO) {

                List<ProductCategoryDTO> childCategoriesDTO = productCategoryExtendRepository.findAllByParentId(parentCategoryDTO.getId())
                    .stream()
                    .map(productCategoryMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));

                JsonNode categoryNode = mapper.convertValue(parentCategoryDTO, JsonNode.class);

                JsonNode subCategoryNodes = mapper.valueToTree(childCategoriesDTO);
                ((ObjectNode) categoryNode).put("children", subCategoryNodes);
                ((ArrayNode) rootNode).add(categoryNode);
            }

            return rootNode;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }
}
