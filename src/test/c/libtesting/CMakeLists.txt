project (testing C)

cmake_minimum_required (VERSION 3.3)

# generate libtesting
include_directories(src)
add_library(testing MODULE src/libtesting)
target_link_libraries(testing)
