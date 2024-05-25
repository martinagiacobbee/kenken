package backtracking;

import model.Cell;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class Problema<P,S> {

    protected abstract boolean assegnabile( P p, S s );
    protected abstract void assegna( P ps, S s );
    protected abstract void deassegna( P ps);
    protected abstract void scriviSoluzione();

    private final P prossimoPuntoDiScelta(List<P> ps, P p ) {
        if( esisteSoluzione(p) ) throw new NoSuchElementException();
        int i=ps.indexOf(p);
        return ps.get(i+1);
    }//prossimoPuntoDiScelta

    protected abstract boolean esisteSoluzione( P p );

    protected abstract boolean ultimaSoluzione( P p );

    //factory
    protected abstract List<P> puntiDiScelta();
    protected abstract Collection<S> scelte(P p );

    protected final boolean tentativo (List<P> ps, P p)
    {
        Collection<S> sa = scelte (p);
        for (S s: sa)
        {
            if (ultimaSoluzione (p) )
                break;
            if (assegnabile (p,s))
            {
                if (esisteSoluzione(p))
                {
                    scriviSoluzione();
                    return true;
                }
                else
                {
                    if (tentativo (ps, prossimoPuntoDiScelta (ps,p)))
                        return true;
                }
                deassegna (p);
            }
        }
        return false;
    } // tentativo

    protected abstract void risolvi();

}