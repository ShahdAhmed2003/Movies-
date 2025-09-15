package com.movieflix.movieApi.dto;

import java.util.List;

public class MoviePageResponse
{
        List<MovieDto> movieDtos;
        Integer pageNumber;
        Integer pageSize;
        long totalElements;//number of records across all the pages
        int totalPages;
        boolean isLast;

        public MoviePageResponse(
                List<MovieDto> movieDtos,
                int pageNumber,
                int pageSize,
                long totalElements,
                int totalPages,
                boolean isLast
        ) {
                this.movieDtos = movieDtos;
                this.pageNumber = pageNumber;
                this.pageSize = pageSize;
                this.totalElements = totalElements;
                this.totalPages = totalPages;
                this.isLast = isLast;
        }


        public void setMovieDtos(List<MovieDto> movieDtos) {
                this.movieDtos = movieDtos;
        }

        public void setPageNumber(Integer pageNumber) {
                this.pageNumber = pageNumber;
        }

        public void setPageSize(Integer pageSize) {
                this.pageSize = pageSize;
        }

        public void setTotalElements(int totalElements) {
                this.totalElements = totalElements;
        }

        public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
        }

        public void setLast(boolean last) {
                isLast = last;
        }

        public List<MovieDto> getMovieDtos() {
                return movieDtos;
        }

        public Integer getPageNumber() {
                return pageNumber;
        }

        public Integer getPageSize() {
                return pageSize;
        }

        public long getTotalElements() {
                return totalElements;
        }

        public int getTotalPages() {
                return totalPages;
        }

        public boolean isLast() {
                return isLast;
        }
}
