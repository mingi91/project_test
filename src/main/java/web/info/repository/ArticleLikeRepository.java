package web.info.repository;

import web.info.domain.LikeDTO;

public interface ArticleLikeRepository {

	void insert(LikeDTO likeDTO);
	void delete(LikeDTO likeDTO);
	LikeDTO get(LikeDTO likeDTO);
}
