package com.github.ovictorpinto.pollmanager.repository;

import com.github.ovictorpinto.pollmanager.model.ChartItem;
import com.github.ovictorpinto.pollmanager.model.Poll;
import com.github.ovictorpinto.pollmanager.model.PollItem;
import com.github.ovictorpinto.pollmanager.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PollRepository {

    private final EntityManager em;

    public PollRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Poll poll) {
        em.merge(poll);
    }

    public List<Poll> listBy(User user) {
        return em.createQuery("select p from Poll p where p.user = :user order by p.title", Poll.class)
                .setParameter("user", user)
                .getResultList();
    }

    public Poll findBy(long id) {
        return em.createQuery("select p from Poll p left join fetch p.itemList where p.id = :id", Poll.class)
                .setParameter("id", id)
                .getResultList().get(0);
    }

    public PollItem findItemBy(long id) {
        return em.createQuery("select p from PollItem p where p.id = :id", PollItem.class)
                .setParameter("id", id)
                .getResultList().get(0);
    }

    public List<ChartItem> findResponseOf(Poll poll) {
        List<Object[]> responses = em.createQuery("select r.pollItem.description, count(*)  from Response r, Poll p " +
                " where p.id = :idPoll " +
                " and r.pollItem member of p.itemList " +
                " group by r.pollItem.description")
                .setParameter("idPoll", poll.getId())
                .getResultList();
        List<ChartItem> collect = responses.stream()
                .map(rs -> new ChartItem(rs[0].toString(), (long) rs[1]))
                .collect(Collectors.toList());
        return collect;
    }

    public void setResponded(long idPoll) {
        Poll poll = findBy(idPoll);
        poll.setHasResponse(true);
        save(poll);
    }
}
